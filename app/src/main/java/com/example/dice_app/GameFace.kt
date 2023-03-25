package com.example.dice_app
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import kotlin.random.Random

class GameFace : AppCompatActivity() {

    //mapping human and computer dice Id's to seperate arrays
    private val HDiceIdset = arrayOf(R.id.iv1, R.id.iv2, R.id.iv3, R.id.iv4, R.id.iv5)
    private val CDiceIdset = arrayOf(R.id.computeroutput1, R.id.computeroutput2, R.id.computeroutput3, R.id.computeroutput4, R.id.computeroutput5)

    private var selectedHDices = mutableListOf<Int>()
    private var selectedCDices = mutableListOf<Int>()
    private var computerDices = arrayOfNulls<Int>(5)
    private var humanDices = arrayOfNulls<Int>(5)

    private var yw=0
    private var cw =0
    private var computerScore = 0
    private var humanScore = 0
    private var rollsno = 0

    //declaring default pass score
    private var passmark = 101


    private var diceThrown = false
    private var selected = false
    private var rollDiceState=false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_face)


        //score button functionality
        val scoreButton = findViewById<Button>(R.id.btn2g)
        scoreButton.setOnClickListener {
            if (diceThrown) {
                totalScore()
                var tempq=0
                if (!rollDiceState){
                    tempq=rollsno

                }else{
                    tempq=3-rollsno
                }
                for (r in 1..(tempq)){
                    selectedCDices= computerDices.filter { it != null && it < 4 } as MutableList<Int>

                    for (i in 0 until 5) {

                        val computerDiceValue = Random.nextInt(6) + 1
                        val commputerDiceImageId = getDiceImageId(computerDiceValue)
                        for (j in selectedCDices) {


                            if (computerDices[i]==j) {
                                findViewById<ImageView>(CDiceIdset[i]).setImageResource(commputerDiceImageId)
                                //push all the values to human dice array
                                computerDices[i] = computerDiceValue

                            }
                        }

                    }
                }

                /*

                Games should provide a fair and level playing field for both human and computer.
                Algorithms that give an unfair advantage to any player are not ethical and can harm the integrity of the game.
                One such algorithm that randomly shuffles the dice to favor the computer player is not ideal. Instead,
                the algorithm should aim to maximize each player's score while considering
                their opponents' scores. This approach ensures that every player has an equal chance to win the game.
                However, it is crucial to note that winning the game should not be the algorithm's sole objective.
                Rather, it should focus on enhancing the player's performance by increasing their score.
                It is possible for a player with a high score to lose the game if their opponents have even higher scores.
                To conclude, a fair and balanced algorithm should treat all
                players equally and maximize each player's score while considering their opponents' scores.
                It ensures that the game remains ethical, enjoyable, and promotes healthy competition. */

                diceThrown = false
            }
        }

        //throw button functionality

        val throwBtn = findViewById<Button>(R.id.btn1g)
        val totalRollsNo = findViewById<TextView>(R.id.totalRolls)

        throwBtn.setOnClickListener {
            if (rollsno < 3) {
                diceThrown = true
                rollDice()
                rollsno++
                // displaying throw button functionality to make the app userfriendly
                totalRollsNo.setText("totalRolls: $rollsno")

            } else if (rollsno == 3) {
                totalScore()
                rollDice()
                selected = false
                rollsno = 0

                totalRollsNo.setText("totalRolls: $rollsno")
            }

        }

        //selected dices action listeners
        for (id in HDiceIdset) {
            var selectedDice=findViewById<ImageView>(id)
            selectedDice.setOnClickListener {
                selected = true
                val diceNumber = getDiceNumber(id)
                selectedHDices.plus(diceNumber)
                if (!selectedHDices.contains(diceNumber)) {
                    selectedHDices.add(diceNumber)


                    val borderWidth = 10 // in pixels
                    val borderColor = ContextCompat.getColor(this, R.color.col12)
                    val shape = GradientDrawable()
                    shape.shape = GradientDrawable.RECTANGLE
                    shape.setStroke(borderWidth, borderColor)
                    selectedDice.background = shape

                }

            }
        }
    }

    //total score calculation
    private fun totalScore() {

        computerScore += computerDices.filterNotNull().sum()
        humanScore += humanDices.filterNotNull().sum()
        //remove testing game tie
        computerScore=8
        humanScore=8
        findViewById<TextView>(R.id.cscoreview).setText("C : "+computerScore.toString())
        findViewById<TextView>(R.id.hscoreview).setText("H : "+humanScore.toString())
        displaythewinner()

    }

    // score update when game tied
    private fun totalScore2(){
        computerScore=0
        humanScore=0
        findViewById<TextView>(R.id.cscoreview).setText("C : "+computerScore.toString())
        findViewById<TextView>(R.id.hscoreview).setText("H : "+humanScore.toString())
        computerScore += computerDices.filterNotNull().sum()
        humanScore += humanDices.filterNotNull().sum()
        findViewById<TextView>(R.id.cscoreview).setText("C : "+computerScore.toString())
        findViewById<TextView>(R.id.hscoreview).setText("H : "+humanScore.toString())
        displaythewinnerwhendraw()

    }

    //roll dices
    private fun rollDice() {
        val random = Random
        rollDiceState=Random.nextBoolean()

        for (instance in 0 until 5) {
            // Generate a random roll for human dices
            val humanDiceValue = random.nextInt(6) + 1
            val humanDiceImageId = getDiceImageId(humanDiceValue)

            //randomize all non hold dices
            if (selected) {
                for (j in selectedHDices) {
                    if (!selectedHDices.contains(instance + 1)) {
                        if (instance !== j - 1) {
                            findViewById<ImageView>(HDiceIdset[instance]).setImageResource(humanDiceImageId)
                            //push values to human dice storing array
                            humanDices[instance] = humanDiceValue
                        }
                    }
                }

            } else {
                findViewById<ImageView>(HDiceIdset[instance]).setImageResource(humanDiceImageId)
                //push values to human dice storing array
                humanDices[instance] = humanDiceValue
            }
            //code executed if the computer player wants to roll the dices
                    if (rollsno!=1){
                        dicesComputerPlayer(instance)

                    }else{
                        if (rollDiceState){
                            rollDiceState=true
                            var mode=intent.getBooleanExtra("selectedMode",true)

                            if (!mode){
                                dicesComputerPlayer(instance)

                            }else{
                                dicesComputerPlayer(instance)

                            }
                        }
                    }
        }

        //clear the selected dices on next iteration
        selected = false
        selectedHDices.clear()

    }

    //Map the relevant image to the image id
    private fun getDiceImageId(diceValue: Int): Int {
        val imageIds = listOf(R.drawable.s1, R.drawable.s2, R.drawable.s3, R.drawable.s4, R.drawable.s5, R.drawable.s6
        )
        if (diceValue < 1 || diceValue > 6) {
            throw IllegalArgumentException("Invalid dice value: $diceValue")
        }
        return imageIds[diceValue - 1]
    }

    //Map the relevant dice number to the id's
    fun getDiceNumber(id: Int): Int {
        val idMap = mapOf(
            R.id.iv1 to 1,
            R.id.iv2 to 2,
            R.id.iv3 to 3,
            R.id.iv4 to 4,
            R.id.iv5 to 5
        )
        return idMap[id] ?: throw IllegalArgumentException("Invalid dice ID")
    }

    //dices generated by computer player
    fun dicesComputerPlayer(dice:Int){
        // Generate a random dice roll for the computer player

        val random = Random
        val computerDiceValue = random.nextInt(6) + 1
        val computerDiceImageId = getDiceImageId(computerDiceValue)
        findViewById<ImageView>(CDiceIdset[dice]).setImageResource(computerDiceImageId)

        //push values to computer dice storing array
        computerDices[dice] = computerDiceValue
    }

    fun displaythewinner(){


        //getting pass mark through Settings page.kt just to set the winning score

        intent?.let {
            val value = it.getIntExtra("MY_INT_VALUE", 0)
            passmark = value
        }
        //if computer wins

        if (computerScore>passmark && computerScore>humanScore){

            val poppy = layoutInflater.inflate(R.layout.losepopup,null)
            val mypop = Dialog(this)
            mypop.setContentView(poppy)
            mypop.setCancelable(true)
            mypop.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            mypop.show()

            val okaybutton = poppy.findViewById<Button>(R.id.okaybtn1)
            okaybutton.setOnClickListener {
                mypop.dismiss()
                var compwinsbanner =findViewById<TextView>(R.id.compwinsbanner)
                cw=cw+1
                compwinsbanner.text ="Computer wins : "+cw.toString()
                computerScore=0
                humanScore=0
                findViewById<TextView>(R.id.cscoreview).setText("C : "+computerScore.toString())
                findViewById<TextView>(R.id.hscoreview).setText("H : "+humanScore.toString())

            }

            val backbtn2 = poppy.findViewById<ImageView>(R.id.b2s)
            backbtn2.setOnClickListener(){
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)

            }
        }

        //if human wins

        else if(humanScore>passmark && humanScore>computerScore){

            //displaying pop screen

            val poppy = layoutInflater.inflate(R.layout.winpopup,null)
            val mypop = Dialog(this)
            mypop.setContentView(poppy)
            mypop.setCancelable(true)
            mypop.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            mypop.show()

            val okaybtn2 = poppy.findViewById<Button>(R.id.okaybtn2)
            okaybtn2.setOnClickListener{
                mypop.dismiss()
                var yourwinsbanner =findViewById<TextView>(R.id.mywinsbanner)
                yw=yw+1
                yourwinsbanner.text ="Your wins           : "+yw.toString()
                computerScore=0
                humanScore=0
                findViewById<TextView>(R.id.cscoreview).setText("C : "+computerScore.toString())
                findViewById<TextView>(R.id.hscoreview).setText("H : "+humanScore.toString())
            }

            val backbtn1 = poppy.findViewById<ImageView>(R.id.b1s)
            backbtn1.setOnClickListener(){
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)

            }

        }
        //if game tied
        else if (humanScore==computerScore && humanScore>passmark){
            //displaying pop screen
            val poppy = layoutInflater.inflate(R.layout.tiepopup,null)
            val mypop = Dialog(this)
            mypop.setContentView(poppy)
            mypop.setCancelable(true)
            mypop.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            mypop.show()
            val okaybtn3 = poppy.findViewById<Button>(R.id.okaybtn3)
            okaybtn3.setOnClickListener {
                mypop.dismiss()
                var hiddenbtn = findViewById<Button>(R.id.btn3g)

                hiddenbtn.visibility =View.VISIBLE
                hiddenbtn.setOnClickListener(){
                    //calling modified functions for one time roll instance
                    rollDice()
                    totalScore2()
                    hiddenbtn.visibility =View.INVISIBLE



                }

            }

        }

    }
    fun displaythewinnerwhendraw(){


        passmark=0
        //if computer wins

        if (computerScore>passmark && computerScore>humanScore){

            val poppy = layoutInflater.inflate(R.layout.losepopup,null)
            val mypop = Dialog(this)
            mypop.setContentView(poppy)
            mypop.setCancelable(true)
            mypop.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            mypop.show()

            val okaybutton = poppy.findViewById<Button>(R.id.okaybtn1)
            okaybutton.setOnClickListener {
                mypop.dismiss()
                var compwinsbanner =findViewById<TextView>(R.id.compwinsbanner)
                cw=cw+1
                compwinsbanner.text ="Computer wins : "+cw.toString()
                computerScore=0
                humanScore=0
                findViewById<TextView>(R.id.cscoreview).setText("C : "+computerScore.toString())
                findViewById<TextView>(R.id.hscoreview).setText("H : "+humanScore.toString())

            }

            val backbtn2 = poppy.findViewById<ImageView>(R.id.b2s)
            backbtn2.setOnClickListener(){
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)

            }
        }

        //if human wins

        else if(humanScore>passmark && humanScore>computerScore){

            //displaying pop screen

            val poppy = layoutInflater.inflate(R.layout.winpopup,null)
            val mypop = Dialog(this)
            mypop.setContentView(poppy)
            mypop.setCancelable(true)
            mypop.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            mypop.show()

            val okaybtn2 = poppy.findViewById<Button>(R.id.okaybtn2)
            okaybtn2.setOnClickListener{
                mypop.dismiss()
                var yourwinsbanner =findViewById<TextView>(R.id.mywinsbanner)
                yw=yw+1
                yourwinsbanner.text ="Your wins           : "+yw.toString()
                computerScore=0
                humanScore=0
                findViewById<TextView>(R.id.cscoreview).setText("C : "+computerScore.toString())
                findViewById<TextView>(R.id.hscoreview).setText("H : "+humanScore.toString())
            }

            val backbtn1 = poppy.findViewById<ImageView>(R.id.b1s)
            backbtn1.setOnClickListener(){
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)

            }
        }
    }

}
