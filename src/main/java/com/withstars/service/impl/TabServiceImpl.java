package com.withstars.service.impl;

import com.withstars.dao.TabMapper;
import com.withstars.domain.Tab;
import com.withstars.service.TabService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TabServiceImpl implements TabService {

    @Autowired
    public TabMapper tabDao;

    @Override
    public List<Tab> getAllTabs() {
        return tabDao.getAllTabs();
    }

    @Override
    public Tab getByTabNameEn(String tabNameEn) {
        return tabDao.getByTabNameEn(tabNameEn);
    }
}
