/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pelotas.connection.score;

import pelotas.ranking.Ranking;
import pelotas.score.Score;

/**
 *
 * @author davidsantiagobarrera
 */
public interface ScoreSaver {

    public Ranking saveScore(Score score);
}
