/* Copyright © 2016 Oracle and/or its affiliates. All rights reserved. */

package com.example.rest.adw;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class MockDAOImpl implements CustomerDAO{
    
    private static final CopyOnWriteArrayList<Customer> eList = new CopyOnWriteArrayList<>();

    static {
      
        Customer c = new Customer("cus_001", "kyle", 
                        "china", "shenzhen", "china", 
                        "china", "123456", "segment");
        eList.add(c);
       
  }
    
    @Override
    public List<Customer> get(Integer num){
        return eList;
    }

}
