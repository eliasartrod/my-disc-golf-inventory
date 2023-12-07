package com.example.inventory.main.bottomnavpages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.inventory.R
import com.example.inventory.common.BaseActivity
import com.example.inventory.common.BaseFragment
import com.example.inventory.common.Event
import com.example.inventory.databinding.FragmentPlayerLookupBinding
import com.example.inventory.databinding.PlayerListCardBinding
import com.example.inventory.main.MainViewModel
import com.example.inventory.model.Player
import com.example.inventory.model.PlayerList
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlayerLookupFragment : BaseFragment() {
    private lateinit var binding: FragmentPlayerLookupBinding
    private lateinit var playerListAdapter: PlayerListAdapter

    private val viewModel: MainViewModel by viewModels()

    override fun getRoot(): View? {
        return binding.root
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlayerLookupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as BaseActivity?)?.showBackButton(true)

        viewModel.loading.observe(viewLifecycleOwner) { setupLoading(it) }
        viewModel.snackBar.observe(viewLifecycleOwner) { showSnackBar(it) }
        viewModel.playerList.observe(viewLifecycleOwner) { setupPlayerList(it) }

        setActionBarTitle(getString(R.string.app_player_lookup_title))
        // setupListeners()

        playerListAdapter = PlayerListAdapter()
        binding.playerList.adapter = playerListAdapter
        binding.playerList.layoutManager = LinearLayoutManager(requireContext())

    }

    private fun setupPlayerList(playerList: PlayerList) {
        playerList.players.forEach {
            playerListAdapter.submitList(it)
        }
    }

    private fun setupLoading(event: Event<Boolean?>) {
        event.contentIfNotHandled?.let {
            binding.progressIndicator.visibility = if (it) View.VISIBLE else View.INVISIBLE
        }
    }

    // TODO: Implement listener for user input to filter specific search
    /*private fun setupListeners() {
        val playerFirstName = binding.playerNameInput.text.toString()
        val playerLastName = binding.playerLastNameInput.text.toString()
        val playerPdgaNumber = binding.playerPdgaNumberInput.text.toString()
        val playerClass = binding.playerClassInput.selectedItem.toString()
        val playerCity = binding.playerCityInput.text.toString()
        val playerStateProvince = binding.playerStateProvinceInput.selectedItem.toString()
        val playerCountry = binding.playerCountryInput.selectedItem.toString()

        viewModel.searchPlayers(
            playerPdgaNumber.toInt(),
            playerLastName,
            playerFirstName,
            playerClass,
            playerCity,
            playerStateProvince,
            playerCountry,
            null,
            null,
            null
        )


    }*/
}

class PlayerListAdapter : RecyclerView.Adapter<PlayerListAdapter.PlayerListViewHolder>() {

    private var players: List<Player> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerListViewHolder {
        val binding =
            PlayerListCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlayerListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlayerListViewHolder, position: Int) {
        val player = players[position]
        holder.bind(player)
    }

    override fun getItemCount(): Int = players.size

    fun submitList(newList: Player) {
        players = listOf(newList)
        notifyDataSetChanged()
    }

    inner class PlayerListViewHolder(private val binding: PlayerListCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(player: Player) {
            player.let {
                binding.playerName.text = "${it.firstName} ${it.lastName}"
                binding.playerPdgaNumber.text = "PDGA: ${it.pdgaNumber}"
                binding.playerRating.text = "Rating: ${it.rating}"
                binding.playerClass.text = "Class: ${it.classification}"
                binding.playerLocation.text =
                    "${it.city}, ${it.stateProv} ${it.country}"
                binding.playerMembershipStatus.text = "Status: ${it.membershipStatus}"
            }
        }
    }
}