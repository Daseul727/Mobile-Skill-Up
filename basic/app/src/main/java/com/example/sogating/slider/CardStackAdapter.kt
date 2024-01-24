package com.example.sogating.slider

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sogating.R
import com.example.sogating.auth.UserDataModel

/*

// 1. 슬라이드 관련 로직을 따로 빼기위해 패키지 및 adapter 를 만들었다
class CardStackAdapter {

}

// 2. 파라미터를 넣어줬다
class CardStackAdapter(val context: Context, val items: List<String>) {


}

// 3. Kotlin 에서의 콜론(:)은 class, interface를 상속/구현할때 사용됨
// CardStackAdapter는 RecyclerView.Adapter의 서브클래스이고, 그 안에서 ViewHolder라는 내부 클래스를 갖는다

class CardStackAdapter(val context: Context, val items: List<String>) : RecyclerView.Adapter<CardStackAdapter.ViewHolder>() {

}

// 4. 위 class~ ViewHolder> 부분을 선언해주고 implement members 클릭하여 아래와 같이 override 한다
class CardStackAdapter(val context: Context, val items: List<String>) : RecyclerView.Adapter<CardStackAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardStackAdapter.ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: CardStackAdapter.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

}

*/

class CardStackAdapter(val context: Context, val items: List<UserDataModel>) : RecyclerView.Adapter<CardStackAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardStackAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view : View = inflater.inflate(R.layout.item_card, parent,false)
        //R import 따로 시켜줘야함

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardStackAdapter.ViewHolder, position: Int) {
        holder.binding(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val nickname = itemView.findViewById<TextView>(R.id.itemNickname)
        val age = itemView.findViewById<TextView>(R.id.itemAge)
        val city = itemView.findViewById<TextView>(R.id.itemCity)

        fun binding(data : UserDataModel) {
            nickname.text = data.nickname
            age.text = data.age
            city.text = data.city
        }
    }
}