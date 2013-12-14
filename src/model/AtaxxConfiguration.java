package model;

import model.ai.AlgorithmEnum;
import model.ai.DifficultyEnum;
import model.board.BoardType;

public class AtaxxConfiguration {

    private static final int BOARD_DEFAULT_SIZE = 7;
    private BoardType boardType;
    private DifficultyEnum difficulty;
    private AlgorithmEnum algorithm;
    private int boardSize;
    private boolean singleToken;
    private boolean gameVSComputer;

    public AtaxxConfiguration() {
        singleToken = false;
        boardSize = BOARD_DEFAULT_SIZE;
        gameVSComputer = true;
        boardType = BoardType.DEFAULT;
        algorithm = AlgorithmEnum.MINIMAX;
        difficulty = DifficultyEnum.APPRENTICE;
    }

    public AlgorithmEnum getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(AlgorithmEnum algorithm) {
        this.algorithm = algorithm;
    }

    public boolean isSingleToken() {
        return singleToken;
    }

    public void setSingleToken(boolean singleToken) {
        this.singleToken = singleToken;
    }

    public boolean isGameVSComputer() {
        return gameVSComputer;
    }

    public void setGameVSComputer(boolean gameVSComputer) {
        this.gameVSComputer = gameVSComputer;
    }

    public DifficultyEnum getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(DifficultyEnum difficulty) {
        this.difficulty = difficulty;
    }

    public BoardType getBoardType() {
        return boardType;
    }

    public void setBoardType(BoardType boardType) {
        this.boardType = boardType;
    }

    public int getBoardSize() {
        return boardSize;
    }

    public void setBoardSize(int boardSize) {
        this.boardSize = boardSize;
    }
}
