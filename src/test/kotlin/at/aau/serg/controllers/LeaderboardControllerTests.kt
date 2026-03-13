package at.aau.serg.controllers

import at.aau.serg.models.GameResult
import at.aau.serg.services.GameResultService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException
import kotlin.test.Test
import kotlin.test.assertEquals
import org.mockito.Mockito.`when` as whenever // when is a reserved keyword in Kotlin

class LeaderboardControllerTests {

    private lateinit var mockedService: GameResultService
    private lateinit var controller: LeaderboardController

    @BeforeEach
    fun setup() {
        mockedService = mock<GameResultService>()
        controller = LeaderboardController(mockedService)
    }

    @Test
    fun test_getLeaderboard_correctScoreSorting() {
        val first = GameResult(1, "first", 20, 20.0)
        val second = GameResult(2, "second", 15, 10.0)
        val third = GameResult(3, "third", 10, 15.0)

        whenever(mockedService.getGameResults()).thenReturn(listOf(second, first, third))

        val res: List<GameResult> = controller.getLeaderboard()

        verify(mockedService).getGameResults()
        assertEquals(3, res.size)
        assertEquals(first, res[0])
        assertEquals(second, res[1])
        assertEquals(third, res[2])
    }

    @Test
    fun test_getLeaderboard_sameScore_CorrectIdSorting() {
        val first = GameResult(1, "first", 20, 20.0)
        val second = GameResult(2, "second", 20, 10.0)
        val third = GameResult(3, "third", 20, 15.0)

        whenever(mockedService.getGameResults()).thenReturn(listOf(second, first, third))

        val res: List<GameResult> = controller.getLeaderboard()

        verify(mockedService).getGameResults()
        assertEquals(3, res.size)
        assertEquals(second, res[0])
        assertEquals(third, res[1])
        assertEquals(first, res[2])
    }

    @Test
    fun test_getLeaderboard_ERROR(){
        val first = GameResult(1, "first", 20, 20.0)
        val second = GameResult(2, "second", 20, 10.0)
        val third = GameResult(3, "third", 20, 15.0)

        whenever(mockedService.getGameResults()).thenReturn(listOf(second, first, third))

        val exception = assertThrows<ResponseStatusException>{
            controller.getLeaderboard(0)
        }

        assertEquals(HttpStatus.BAD_REQUEST, exception.statusCode)
    }
    @Test
    fun test_getLeaderboard_WithRank(){
        val first = GameResult(1, "first", 20, 20.0)    //2,3,1,5,4
        val second = GameResult(2, "second", 20, 10.0)
        val third = GameResult(3, "third", 20, 15.0)
        val fourth = GameResult(4, "fourth", 19, 15.0)
        val fith = GameResult(5, "fith", 19, 14.0)

        whenever(mockedService.getGameResults()).thenReturn(listOf(second, first, third, fourth, fith))
        val res = controller.getLeaderboard(1)

        assertEquals(4, res.size)
        assertEquals(second,res[0])
        assertEquals(third,res[1])
        assertEquals(first,res[2])
        assertEquals(fith,res[3])
    }

    @Test
    fun test_getLeaderboard_WithRankGreaterSize(){
        val first = GameResult(1, "first", 20, 20.0)
        val second = GameResult(2, "second", 20, 10.0)
        val third = GameResult(3, "third", 20, 15.0)
        val fourth = GameResult(4, "fourth", 19, 15.0)
        val fith = GameResult(5, "fith", 19, 14.0)

        whenever(mockedService.getGameResults()).thenReturn(listOf(second, first, third, fourth, fith))
        val exception = assertThrows<ResponseStatusException>{
            controller.getLeaderboard(6)
        }

        assertEquals(HttpStatus.BAD_REQUEST, exception.statusCode)
    }

    /*@Test
    fun test_getLeaderboard_WithRanktest(){
        val first = GameResult(1, "first", 20, 20.0)    //2,3,1,5,4
        val second = GameResult(2, "second", 20, 10.0)
        val third = GameResult(3, "third", 20, 15.0)
        val fourth = GameResult(4, "fourth", 19, 15.0)
        val fith = GameResult(5, "fith", 19, 14.0)

        whenever(mockedService.getGameResults()).thenReturn(listOf())
        val res = controller.getLeaderboard(1)

        assertEquals(4, res.size)
        assertEquals(second,res[0])
        assertEquals(third,res[1])
        assertEquals(first,res[2])
        assertEquals(fith,res[3])
    }*/

}

