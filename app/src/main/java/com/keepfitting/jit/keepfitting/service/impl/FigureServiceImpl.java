package com.keepfitting.jit.keepfitting.service.impl;

import android.content.Context;

import com.keepfitting.jit.keepfitting.dao.FigureDao;
import com.keepfitting.jit.keepfitting.dao.impl.FigureDaoImpl;
import com.keepfitting.jit.keepfitting.entity.Figure;
import com.keepfitting.jit.keepfitting.service.FigureService;

import java.util.List;

/**
 * Created by 14032 on 2020/6/23.
 */

public class FigureServiceImpl implements FigureService {

    private FigureDao figureDao;

    public FigureServiceImpl(Context context) {
        figureDao=new FigureDaoImpl(context);

    }

    /**
     * 查询数据
     *
     * @param userId
     * @param figuretype
     */
    @Override
    public List<Figure> findFigureByType(int userId, String figuretype) {
        return figureDao.findFigureByType(userId,figuretype);
    }

    @Override
    public Figure findRecentByType(int userId, String figuretype) {
        int size = figureDao.findFigureByType(userId,figuretype).size();
        if (size==0){
            return new Figure();
        }
        return figureDao.findFigureByType(userId,figuretype).get(size-1);
    }

    /**
     * 添加数据
     *
     * @param figure
     * @return
     */
    @Override
    public boolean addFigure(Figure figure) {
        int size = figureDao.findFigureByType(figure.getUserId(),figure.getFigureType()).size();
        if (size!=0){
            Figure recentFigure = figureDao.findFigureByType(figure.getUserId(),figure.getFigureType()).get(size-1);
            if (recentFigure.getRecordDate().equals(figure.getRecordDate())) {
                return false;
            }
        }
        figureDao.addFigure(figure);
        return true;
    }

    /**
     * 修改数据
     *
     * @param figure
     * @return
     */
    @Override
    public boolean modifyFigure(Figure figure) {
        try {
            figureDao.modifyFigure(figure);
            return true;
        }catch (Exception e){
            //
            return false;
        }
    }
}
