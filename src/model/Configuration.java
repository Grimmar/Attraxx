package model;

import model.ai.AlgorithmType;
import model.ai.DifficultyType;
import model.board.BoardType;

public interface Configuration {

    public AlgorithmType getAlgorithm();

    public void setAlgorithm(AlgorithmType algorithm);

    public boolean isSingleToken();

    public void setSingleToken(boolean singleToken);

    public boolean isGameVSComputer();

    public void setGameVSComputer(boolean gameVSComputer);

    public DifficultyType getDifficulty();

    public void setDifficulty(DifficultyType difficulty);

    public BoardType getBoardType();

    public void setBoardType(BoardType boardType);

    public int getBoardSize();

    public void setBoardSize(int boardSize);
}
