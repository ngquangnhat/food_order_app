package com.example.newfoodorder.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.newfoodorder.R
import com.example.newfoodorder.databinding.FragmentCartBinding
import com.example.newfoodorder.event.ReloadListCartEvent
import com.example.newfoodorder.view.BaseFragment
import com.example.newfoodorder.view.activity.MainActivity
import com.example.newfoodorder.viewmodel.CartViewModel
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class CartFragment : BaseFragment() {
    private lateinit var cartViewModel: CartViewModel

    override fun initToolbar() {
        (activity as MainActivity).setToolBar(true, getString(R.string.cart))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentCartBinding = FragmentCartBinding.inflate(inflater, container, false)
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }
        cartViewModel = CartViewModel(requireActivity())
        fragmentCartBinding.cartViewModel = cartViewModel
        fragmentCartBinding.lifecycleOwner = viewLifecycleOwner

        return fragmentCartBinding.root
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: ReloadListCartEvent?) {
        cartViewModel.getListFoodInCartOK()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cartViewModel.release()
    }

}