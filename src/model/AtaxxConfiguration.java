package model;

import model.ai.AlgorithmType;
import model.ai.DifficultyType;
import model.board.BoardType;

public class AtaxxConfiguration implements Configuration {

    private static final int BOARD_DEFAULT_SIZE = 7;
    private BoardType boardType;
    private DifficultyType difficulty;
    private AlgorithmType algorithm;
    private int boardSize;
    private boolean singleToken;
    private boolean gameVSComputer;

    public AtaxxConfiguration() {
        singleToken = false;
        boardSize = BOARD_DEFAULT_SIZE;
        gameVSComputer = true;
        boardType = BoardType.DEFAULT;
        algorithm = AlgorithmType.MINIMAX;
        difficulty = DifficultyType.APPRENTICE;
    }

    public AlgorithmType getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(AlgorithmType algorithm) {
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

    public DifficultyType getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(DifficultyType difficulty) {
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
