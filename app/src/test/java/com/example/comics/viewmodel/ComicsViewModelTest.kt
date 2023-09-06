@file:OptIn(ExperimentalCoroutinesApi::class)

package com.example.comics.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.comics.model.DataModel
import com.example.comics.model.ItemModel
import com.example.comics.repository.ComicsRepository
import com.example.comics.util.Result
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.hamcrest.core.Is
import org.hamcrest.core.IsInstanceOf
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.lang.Exception

class ComicsViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private val repository = mockk<ComicsRepository>()

    private val viewModel = ComicsViewModel(repository = repository)

    val dispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when execute getComics result must be not empty`() = runBlocking {

        val response = ItemModel(data = DataModel(results = emptyList()))
        coEvery { repository.getComics() } returns response

        viewModel.getListComics()

        val value = viewModel.result.getOrAwaitValue()

        Assert.assertNotNull(value)
    }

    @Test
    fun `when execute getComics error `() = runBlocking {
        coEvery { repository.getComics() } coAnswers { throw Exception("Falha ao buscar items") }

        viewModel.getListComics()

        val value = viewModel.result.getOrAwaitValue()
        org.hamcrest.MatcherAssert.assertThat(value, Is(IsInstanceOf(Result::class.java)))
    }
}