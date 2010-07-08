/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.opensocial.service;


/**
 * <a href="GadgetLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link GadgetLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       GadgetLocalService
 * @generated
 */
public class GadgetLocalServiceWrapper implements GadgetLocalService {
	public GadgetLocalServiceWrapper(GadgetLocalService gadgetLocalService) {
		_gadgetLocalService = gadgetLocalService;
	}

	public com.liferay.opensocial.model.Gadget addGadget(
		com.liferay.opensocial.model.Gadget gadget)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _gadgetLocalService.addGadget(gadget);
	}

	public com.liferay.opensocial.model.Gadget createGadget(long gadgetId) {
		return _gadgetLocalService.createGadget(gadgetId);
	}

	public void deleteGadget(long gadgetId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_gadgetLocalService.deleteGadget(gadgetId);
	}

	public void deleteGadget(com.liferay.opensocial.model.Gadget gadget)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_gadgetLocalService.deleteGadget(gadget);
	}

	@SuppressWarnings("unchecked")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _gadgetLocalService.dynamicQuery(dynamicQuery);
	}

	@SuppressWarnings("unchecked")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _gadgetLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	@SuppressWarnings("unchecked")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _gadgetLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _gadgetLocalService.dynamicQueryCount(dynamicQuery);
	}

	public com.liferay.opensocial.model.Gadget getGadget(long gadgetId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _gadgetLocalService.getGadget(gadgetId);
	}

	public java.util.List<com.liferay.opensocial.model.Gadget> getGadgets(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _gadgetLocalService.getGadgets(start, end);
	}

	public int getGadgetsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _gadgetLocalService.getGadgetsCount();
	}

	public com.liferay.opensocial.model.Gadget updateGadget(
		com.liferay.opensocial.model.Gadget gadget)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _gadgetLocalService.updateGadget(gadget);
	}

	public com.liferay.opensocial.model.Gadget updateGadget(
		com.liferay.opensocial.model.Gadget gadget, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _gadgetLocalService.updateGadget(gadget, merge);
	}

	public com.liferay.opensocial.model.Gadget addGadget(long companyId,
		java.lang.String name, java.lang.String url)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _gadgetLocalService.addGadget(companyId, name, url);
	}

	public void destroyGadget(long companyId, long gadgetId,
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_gadgetLocalService.destroyGadget(companyId, gadgetId, name);
	}

	public void destroyGadgets()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_gadgetLocalService.destroyGadgets();
	}

	public java.util.List<com.liferay.opensocial.model.Gadget> getGadgets(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _gadgetLocalService.getGadgets(companyId, start, end);
	}

	public int getGadgetsCount(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _gadgetLocalService.getGadgetsCount(companyId);
	}

	public void initGadget(long companyId, long gadgetId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_gadgetLocalService.initGadget(companyId, gadgetId, name);
	}

	public void initGadgets()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_gadgetLocalService.initGadgets();
	}

	public com.liferay.opensocial.model.Gadget updateGadget(long companyId,
		long gadgetId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _gadgetLocalService.updateGadget(companyId, gadgetId, name);
	}

	public GadgetLocalService getWrappedGadgetLocalService() {
		return _gadgetLocalService;
	}

	private GadgetLocalService _gadgetLocalService;
}