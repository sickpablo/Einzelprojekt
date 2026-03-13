package at.aau.serg.controllers

import at.aau.serg.models.GameResult
import at.aau.serg.services.GameResultService
import org.junit.jupiter.api.BeforeEach
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when` as whenever
import kotlin.test.Test
import kotlin.test.assertEquals

class GameResultControllerTests {

    private lateinit var mockedService: GameResultService
    private lateinit var controller: GameResultController

    @BeforeEach
    fun setup(){
        mockedService = mock<GameResultService>()
        controller = GameResultController(mockedService)
    }

    @Test
    fun test_getAllGameResults(){
        var first = GameResult(1, "first", 20, 20.0)
        var second = GameResult(2, "second", 20, 20.0)
        var third = GameResult(3, "third", 20, 20.0)

        whenever(mockedService.getGameResults()).thenReturn(listOf(first, second, third))

        val res = controller.getAllGameResults()

        assertEquals(3,res.size)
        assertEquals(first,res[0])
        assertEquals(second,res[1])
        assertEquals(third,res[2])
    }
    @Test
    fun test_getGameResult(){
        var first = GameResult(1, "first", 20, 20.0)


        whenever(mockedService.getGameResult(1)).thenReturn(first)

        val res = controller.getGameResult(1)

        assertEquals(first,res)

    }

    @Test
    fun test_addGameResult(){
        var first = GameResult(1, "first", 20, 20.0)

        controller.addGameResult(first)
        verify(mockedService).addGameResult(first)
    }

    @Test
    fun test_deleteGameResult(){

        controller.deleteGameResult(1)
        verify(mockedService).deleteGameResult(1)
    }
}