package com.keepfitting.jit.keepfitting.service;

import com.keepfitting.jit.keepfitting.entity.Figure;

import java.util.List;

/**
 * Created by 14032 on 2020/6/23.
 */

public interface FigureService {
    /**
     * 查询数据
     */
    public List<Figure> findFigureByType(int userId, String figuretype) ;
    public Figure findRecentByType(int userId, String figuretype) ;

    /**
     * 添加数据
     * @return
     */
    public boolean addFigure(Figure figure) ;

    /**
     * 修改数据
     * @return
     */
    public boolean modifyFigure(Figure figure) ;
}
