/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.concurrent.Worker;
import model.ai.AlgorithmType;
import model.ai.DifficultyType;

import java.util.List;

/**
 *
 * @author David
 */
public interface Model {
    void generate(Configuration c);
    TileModel get(int x, int y);
    void move(TileModel begin, TileModel end) throws IllegalAccessException;
    boolean canMoveTo(TileModel begin, TileModel end);
    List<TileModel> getPossibleMoves(TileModel source);
    boolean isMoveValid(TileModel source, TileModel destination);
    boolean canPlay();
    void play();
    boolean isCurrentPlayerTurn(Owner owner);
    int getBoardSize();
    void print();
    void setAlgorithm(AlgorithmType a, DifficultyType d);
    boolean isGameVSComputer();
    boolean isGameOver();
    Owner getWinner();
    public IntegerProperty numberOfPlayProperty();
    public IntegerProperty blueTokensProperty();
    public IntegerProperty redTokensProperty();
    ReadOnlyObjectProperty<Worker.State> algorithmStateProperty();
}
