@file:OptIn(ExperimentalCoroutinesApi::class)

package com.example.comics.viewmodel

import com.example.comics.CoroutinesTestRule
import com.example.comics.model.DataModel
import com.example.comics.model.ItemModel
import com.example.comics.repository.ComicsRepository
import com.example.comics.util.Result
import com.example.comics.viewmodel.util.getOrAwaitValue
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.core.Is
import org.hamcrest.core.IsInstanceOf
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

class ComicsViewModelTest {

    @get:Rule
    var coroutinesTestRule= CoroutinesTestRule()

    private val repository = mockk<ComicsRepository>()

    private val viewModel = ComicsViewModel(repository = repository)

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