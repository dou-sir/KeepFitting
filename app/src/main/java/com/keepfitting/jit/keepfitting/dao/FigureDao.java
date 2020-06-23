package com.keepfitting.jit.keepfitting.dao;

import com.keepfitting.jit.keepfitting.entity.Figure;

import java.util.List;

public interface FigureDao {
    /**
     * 查询数据
     */
    public List<Figure> findFigureByType(int userId, String figuretype) ;

    /**
     * 添加数据
     * @return
     */
    public Figure addFigure(Figure figure) ;

    /**
     * 修改数据
     * @return
     */
    public void modifyFigure(Figure figure) ;

    /**
     * 删除数据
     * @return
     */
    public void deleteFigure(Figure figure) ;
}
