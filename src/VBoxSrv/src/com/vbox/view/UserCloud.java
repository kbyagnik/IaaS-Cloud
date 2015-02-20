package com.vbox.view;

import java.util.List;

import org.virtualbox_4_3.IMachine;
import org.virtualbox_4_3.IVirtualBox;
import org.virtualbox_4_3.VBoxException;
import org.virtualbox_4_3.VirtualBoxManager;

public class UserCloud {
	VirtualBoxManager mgr;
	IVirtualBox vbox;
	List<IMachine> vmList;
	boolean isConnected = false;

	public UserCloud() {
		mgr = VirtualBoxManager.createInstance(null);
		System.out.println("mgr created");
	}

	public boolean connect() {
		try {
			mgr.connect("http://localhost:18083", "", "");
			vbox = mgr.getVBox();
			System.out.println("VirtualBox version: " + vbox.getVersion());
			vmList = vbox.getMachines();
			return true;
		} catch (VBoxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public VirtualBoxManager getMgr() {
		return mgr;
	}

	public void setMgr(VirtualBoxManager mgr) {
		this.mgr = mgr;
	}

	public IVirtualBox getVbox() {
		return vbox;
	}

	public void setVbox(IVirtualBox vbox) {
		this.vbox = vbox;
	}

	public List<IMachine> getVmList() {
		return vmList;
	}

	public void setVmList(List<IMachine> vmList) {
		this.vmList = vmList;
	}

	public boolean isConnected() {
		return isConnected;
	}

	public void setConnected(boolean isConnected) {
		this.isConnected = isConnected;
	}

	
}
