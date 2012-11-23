/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pelotas.ranking;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import pelotas.score.Score;

/**
 *
 * @author davidsantiagobarrera
 */
public class Ranking implements Serializable{

    private List <Score>scores;

    public Ranking() {
        this.scores = new ArrayList();
    }

    public List<Score> getScores() {
        return scores;
    }

    public void setScores(Score score) {
        this.scores.add(score);
    }

}
