/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.amazontools;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.autoscaling.model.DeleteLaunchConfigurationRequest;
import com.amazonaws.services.autoscaling.model.DescribeLaunchConfigurationsRequest;
import com.amazonaws.services.autoscaling.model.DescribeLaunchConfigurationsResult;
import com.amazonaws.services.autoscaling.model.LaunchConfiguration;
import com.amazonaws.services.autoscaling.model.ResourceInUseException;
import com.amazonaws.services.ec2.model.DeleteVolumeRequest;
import com.amazonaws.services.ec2.model.DeregisterImageRequest;
import com.amazonaws.services.ec2.model.DescribeImagesRequest;
import com.amazonaws.services.ec2.model.DescribeImagesResult;
import com.amazonaws.services.ec2.model.DescribeInstancesResult;
import com.amazonaws.services.ec2.model.DescribeVolumesRequest;
import com.amazonaws.services.ec2.model.DescribeVolumesResult;
import com.amazonaws.services.ec2.model.Filter;
import com.amazonaws.services.ec2.model.Image;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.Reservation;
import com.amazonaws.services.ec2.model.Volume;
import com.amazonaws.services.identitymanagement.model.GetUserResult;
import com.amazonaws.services.identitymanagement.model.User;

import jargs.gnu.CmdLineParser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Ivica Cardic
 * @author Mladen Cikara
 */
public class AMICleaner extends BaseAMITool {

	public static void main(String[] args) throws Exception {
		CmdLineParser cmdLineParser = new CmdLineParser();

		CmdLineParser.Option propertiesFileNameOption =
			cmdLineParser.addStringOption("properties.file.name");

		cmdLineParser.parse(args);

		try {
			new AMICleaner(
				(String)cmdLineParser.getOptionValue(propertiesFileNameOption));
		}
		catch (Exception e) {
			e.printStackTrace();

			System.exit(-1);

			return;
		}

		System.exit(0);
	}

	public AMICleaner(String propertiesFileName) throws Exception {
		super(propertiesFileName);

		System.out.println("Deleting available Volumes");

		deleteAvailableVolumes();

		System.out.println("Deleting old Launch Configurations");

		deleteOldLaunchConfigurations();

		System.out.println("Deleting unused AMIs");

		deleteOldAMIs();
	}

	protected void deleteAvailableVolumes() {
		DescribeVolumesRequest describeVolumesRequest =
			new DescribeVolumesRequest();

		Filter filter = new Filter();

		filter.setName("status");

		filter.withValues("available");

		describeVolumesRequest.withFilters(filter);

		DescribeVolumesResult describeVolumesResult =
			amazonEC2Client.describeVolumes(describeVolumesRequest);

		List<Volume> volumes = describeVolumesResult.getVolumes();

		for (int i = 0; i < volumes.size(); i++) {
			DeleteVolumeRequest deleteVolumeRequest = new DeleteVolumeRequest();

			Volume volume = volumes.get(i);

			deleteVolumeRequest.setVolumeId(volume.getVolumeId());

			amazonEC2Client.deleteVolume(deleteVolumeRequest);
		}
	}

	protected void deleteOldAMIs() {
		Set<String> imageIds = getImageIds();

		List<String> unusedImageIds = getUnusedImageIds(getUserId(), imageIds);

		for (String imageId : unusedImageIds) {
			deleteAMI(imageId);
		}
	}

	protected void deleteOldLaunchConfigurations() {
		DescribeLaunchConfigurationsRequest
			describeLaunchConfigurationsRequest =
				new DescribeLaunchConfigurationsRequest();

		DescribeLaunchConfigurationsResult describeLaunchConfigurationsResult =
			amazonAutoScalingClient.describeLaunchConfigurations(
				describeLaunchConfigurationsRequest);

		List<LaunchConfiguration> launchConfigurations =
			describeLaunchConfigurationsResult.getLaunchConfigurations();

		for (int i = 0; i < launchConfigurations.size(); i++) {
			DeleteLaunchConfigurationRequest deleteLaunchConfigurationRequest =
				new DeleteLaunchConfigurationRequest();

			LaunchConfiguration launchConfiguration = launchConfigurations.get(
				i);

			deleteLaunchConfigurationRequest.setLaunchConfigurationName(
				launchConfiguration.getLaunchConfigurationName());

			try {
				amazonAutoScalingClient.deleteLaunchConfiguration(
					deleteLaunchConfigurationRequest);
			}
			catch (ResourceInUseException riue) {
			}
		}
	}

	protected Set<String> getImageIds() {
		DescribeInstancesResult describeInstancesResult =
			amazonEC2Client.describeInstances();

		Set<String> imageIds = new HashSet<String>();

		for (Reservation reservation :
				describeInstancesResult.getReservations()) {

			for (Instance instance : reservation.getInstances()) {
				imageIds.add(instance.getImageId());
			}
		}

		return imageIds;
	}

	protected List<String> getUnusedImageIds(
		String userId, Set<String> imageIds) {

		DescribeImagesRequest describeImagesRequest =
			new DescribeImagesRequest();

		ArrayList<String> owners = new ArrayList<String>();

		owners.add(userId);

		describeImagesRequest.setOwners(owners);

		DescribeImagesResult describeImagesResult =
			amazonEC2Client.describeImages(describeImagesRequest);

		List<Image> images = describeImagesResult.getImages();

		List<String> unusedImageIds = new ArrayList<String>();

		for (Image image : images) {
			String imageName = image.getName();

			if ((imageName != null) && imageName.startsWith("osb-lcs-") &&
				!imageIds.contains(image.getImageId())) {

				unusedImageIds.add(image.getImageId());
			}
		}

		return unusedImageIds;
	}

	protected String getUserId() {
		String userId = null;

		try {
			GetUserResult getUserResult =
				amazonIdentityManagementClient.getUser();

			User user = getUserResult.getUser();

			userId = user.getUserId();
		}
		catch (AmazonServiceException e) {
			String errorCode = e.getErrorCode();

			if (errorCode.compareTo("AccessDenied") == 0) {
				String message = e.getMessage();

				int start = message.indexOf("::");
				int end = message.indexOf(":", start + 2);

				userId = message.substring(start + 2, end);
			}
		}

		return userId;
	}

	private void deleteAMI(String imageId) {
		DeregisterImageRequest deregisterImageRequest =
			new DeregisterImageRequest();

		deregisterImageRequest.setImageId(imageId);

		amazonEC2Client.deregisterImage(deregisterImageRequest);
	}

}