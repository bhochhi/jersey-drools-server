package com.telogical.ksp.model;

import com.telogical.ksp.util.PackageStore;


public class TestXML {
    public static void main(String[] args) throws Exception {
    	System.out.println("Store Size:"+PackageStore.getStore().size());
    	System.out.println(PackageStore.getStore().get("KSPManager_Comcast_ProductionTP"));
    	
    	    	
    }
}