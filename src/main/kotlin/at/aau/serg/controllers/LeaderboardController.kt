package at.aau.serg.controllers

import at.aau.serg.models.GameResult
import at.aau.serg.services.GameResultService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/leaderboard")
class LeaderboardController(
    private val gameResultService: GameResultService
) {

    @GetMapping
    fun getLeaderboard(@RequestParam(required = false) rank: Int?= null): List<GameResult> {
       val lb = gameResultService.getGameResults().sortedWith(compareByDescending<GameResult> { it.score }.thenBy { it.timeInSeconds })
        if(rank != null) {
            val i = rank - 1

            if (i < 0 || rank > lb.size) {
                throw ResponseStatusException(HttpStatus.BAD_REQUEST)

            }
                  return lb.filterIndexed { index, _ -> index >= i - 3 && index <= i + 3 }

        }
        else{
            return lb

        }
    }
}
            /*var newList = lb.toMutableList()*/

    /*if(rank != null && rank > 0){
        lb.get(rank)
        newList.add(lb.get(rank-3))
        newList.add(lb.get(rank+3))
    }*/


