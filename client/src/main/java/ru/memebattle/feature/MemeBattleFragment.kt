package ru.memebattle.feature

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.nkzawa.emitter.Emitter
import com.github.nkzawa.socketio.client.IO
import com.github.nkzawa.socketio.client.Socket
import kotlinx.android.synthetic.main.fragment_memebattle.*
import org.json.JSONObject
import ru.memebattle.R


class MemeBattleFragment : Fragment() {

    private lateinit var socket: Socket

    private val memesListener = Emitter.Listener {
        val data = it[0] as JSONObject


    }

    private val resultListener = Emitter.Listener {
        val data = it[0] as JSONObject


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_memebattle, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        socket = IO.socket("http://chat.socket.io")
        socket.connect()

        image1.setOnClickListener {
            socket.emit("like", "1")
        }

        image2.setOnClickListener {
            socket.emit("like", "2")
        }

        socket.on("memes", memesListener)
        socket.on("result", resultListener)
    }

    override fun onStop() {
        super.onStop()

        socket.disconnect()
    }
}