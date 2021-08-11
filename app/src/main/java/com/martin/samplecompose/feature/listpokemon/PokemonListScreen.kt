package com.martin.samplecompose.feature.listpokemon

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.coil.rememberCoilPainter
import com.martin.samplecompose.data.remote.models.PokedexListEntry

@Composable
fun PokemonListScreen(
    navController: NavController,
    viewModel: ListPokemonViewModel = hiltViewModel()
) {
    viewModel.loadPokemonList()
    val pokemonList by remember { viewModel.pokemonList }
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        items(
            items = pokemonList,
            itemContent = {
                PokemonListItem(pokemon = it, navController = navController)
            }
        )
    }
}

@Composable
fun PokemonListItem(
    pokemon: PokedexListEntry,
    navController: NavController,
) {
    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .fillMaxWidth(),
        elevation = 2.dp,
        backgroundColor = Color.White,
        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
    ) {
        Row(Modifier.clickable {

            navController.navigate(
                "pokemon_detail_screen/${pokemon.pokemonName}"
            )
        }) {
            PokemonImage(pokemon)
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically)
            ) {
                Text(text = pokemon.pokemonName, style = MaterialTheme.typography.h6)
            }
        }
    }
}

@Composable
private fun PokemonImage(pokemon: PokedexListEntry) {
    Image(
        painter = rememberCoilPainter(
            request = pokemon.imageUrl,

            ),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .padding(8.dp)
            .size(84.dp)
            .clip(RoundedCornerShape(corner = CornerSize(16.dp)))
    )
}

@Preview
@Composable
fun PreviewPokemonItem() {
    val pokemon = PokedexListEntry(
        "joni",
        "https://w7.pngwing.com/pngs/808/925/png-transparent-mareep-pokemon-crystal-pokemon-go-flaaffy-pokemon-go-chibi-pokemon-ampharos.png",
        1
    )
    PokemonListItem(pokemon = pokemon, NavController(LocalContext.current))
}