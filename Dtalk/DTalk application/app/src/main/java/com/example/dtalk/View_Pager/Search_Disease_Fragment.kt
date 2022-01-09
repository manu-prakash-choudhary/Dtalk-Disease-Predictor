package com.example.dtalk.View_Pager

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StableIdKeyProvider
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dtalk.Model_Class.symptoms_nameList
import com.example.dtalk.R
import com.example.dtalk.ml.Disease1

import kotlinx.android.synthetic.main.fragment_search__disease_.*

import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class Search_Disease_Fragment : Fragment() {


    private var param1: String? = null
    private var param2: String? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_search__disease_, container, false)


//        diplaying_Symptoms()

//        val sy1 = v.findViewById<EditText>(R.id.sy1)
//        val sy2 = v.findViewById<EditText>(R.id.sy2)
//        val sy3 = v.findViewById<EditText>(R.id.sy3)
//
//        val sy1_card = v.findViewById<CardView>(R.id.sy1_card)
//        val sy2_card = v.findViewById<CardView>(R.id.sy2_card)
//        val sy3_card = v.findViewById<CardView>(R.id.sy3_card)
//
//        val recy_card = v.findViewById<CardView>(R.id.recy_card)


//        diplaying_Symptoms()

        val myRecycler = v.findViewById<RecyclerView>(R.id.myRecycler)
        val predict_button = v.findViewById<Button>(R.id.predict_button)
        val back_button = v.findViewById<Button>(R.id.back_button)
        val doctors_recommend_button = v.findViewById<Button>(R.id.doctors_recommend_button)

        val results = v.findViewById<TextView>(R.id.results)
        val Disease_name = v.findViewById<TextView>(R.id.Disease_name)
        val precaution_name = v.findViewById<TextView>(R.id.precaution_name)
        val precaution_results = v.findViewById<TextView>(R.id.precaution_results)
        val Doctors_name = v.findViewById<TextView>(R.id.Doctors_name)
        val Doctors_results = v.findViewById<TextView>(R.id.Doctors_results)




        myRecycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        myRecycler.setHasFixedSize(true)

        myRecycler.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL))


//        //calling function saving the return arraylist of symptom in the variable
//        val complete_Symp_List = diplaying_Symptoms()
//

//        //made the object of the class
//        val obj = showSymptoms(complete_Symp_List,requireContext())
//
//        //taking the selected Symptoms List from the class showSymptoms()
//        val selectedlist = obj.symptomSelectedList


        predict_button.setOnClickListener {


            val v1 = sy1.text.toString().toLowerCase()
            val v2 = sy2.text.toString().toLowerCase()
            val v3 = sy3.text.toString().toLowerCase()


            val Inputarray= ArrayList<String>()
            Inputarray.add(v1)
            Inputarray.add(v2)
            Inputarray.add(v3)



            ML_model(Inputarray)

            myRecycler.visibility = View.INVISIBLE
            sy1.visibility =View.INVISIBLE
            sy2.visibility =View.INVISIBLE
            sy3.visibility =View.INVISIBLE
            sy1_card.visibility =View.INVISIBLE
            sy2_card.visibility =View.INVISIBLE
            sy3_card.visibility =View.INVISIBLE

//            recy_card.visibility =View.INVISIBLE
            predict_button.visibility = View.INVISIBLE
            text_msg.visibility = View.INVISIBLE

            back_button.visibility = View.VISIBLE
            doctors_recommend_button.visibility = View.VISIBLE

            Disease_name.visibility = View.VISIBLE
            results.visibility = View.VISIBLE
            precaution_name.visibility = View.VISIBLE
            precaution_results.visibility = View.VISIBLE
            Doctors_name.visibility = View.VISIBLE
            Doctors_results.visibility = View.VISIBLE


        }

        back_button.setOnClickListener {

            myRecycler.visibility = View.VISIBLE
            sy1.visibility =View.VISIBLE
            sy2.visibility =View.VISIBLE
            sy3.visibility =View.VISIBLE
            sy1_card.visibility =View.VISIBLE
            sy2_card.visibility =View.VISIBLE
            sy3_card.visibility =View.VISIBLE
//            recy_card.visibility =View.VISIBLE

            predict_button.visibility = View.VISIBLE
            back_button.visibility = View.INVISIBLE

            doctors_recommend_button.visibility = View.INVISIBLE
            Disease_name.visibility = View.INVISIBLE
            results.visibility = View.INVISIBLE
            precaution_name.visibility = View.INVISIBLE
            precaution_results.visibility = View.INVISIBLE
            Doctors_name.visibility = View.INVISIBLE
            Doctors_results.visibility = View.INVISIBLE

        }


        val displaySymp = ArrayList<symptoms_nameList>()

        displaySymp.add(symptoms_nameList("Abdominal Pain"))
        displaySymp.add(symptoms_nameList("abdominal pain"))
        displaySymp.add(symptoms_nameList("abnormal menstruation"))
        displaySymp.add(symptoms_nameList("acidity"))
        displaySymp.add(symptoms_nameList("acute liver failure"))
        displaySymp.add(symptoms_nameList("altered sensorium"))
        displaySymp.add(symptoms_nameList("anxiety"))
        displaySymp.add(symptoms_nameList("back pain"))
        displaySymp.add(symptoms_nameList("belly pain"))
        displaySymp.add(symptoms_nameList("blackheads"))
        displaySymp.add(symptoms_nameList("bladder discomfort"))
        displaySymp.add(symptoms_nameList("blister"))
        displaySymp.add(symptoms_nameList("blood in sputum"))
        displaySymp.add(symptoms_nameList("bloody stool"))
        displaySymp.add(symptoms_nameList("blurred and distorted vision"))
        displaySymp.add(symptoms_nameList("breathlessness"))
        displaySymp.add(symptoms_nameList("brittle nails"))
        displaySymp.add(symptoms_nameList("bruising"))
        displaySymp.add(symptoms_nameList("burning micturition"))
        displaySymp.add(symptoms_nameList("chest pain"))
        displaySymp.add(symptoms_nameList("chills"))
        displaySymp.add(symptoms_nameList("cold hands and feets"))
        displaySymp.add(symptoms_nameList("coma"))
        displaySymp.add(symptoms_nameList("congestion"))
        displaySymp.add(symptoms_nameList("constipation"))
        displaySymp.add(symptoms_nameList("continuous feel of urine"))
        displaySymp.add(symptoms_nameList("continuous sneezing"))
        displaySymp.add(symptoms_nameList("cough"))
        displaySymp.add(symptoms_nameList("cramps"))
        displaySymp.add(symptoms_nameList("dark urine"))
        displaySymp.add(symptoms_nameList("dehydration"))
        displaySymp.add(symptoms_nameList("depression"))
        displaySymp.add(symptoms_nameList("diarrhoea"))
        displaySymp.add(symptoms_nameList("dischromic patches"))
        displaySymp.add(symptoms_nameList("distention of abdomen"))
        displaySymp.add(symptoms_nameList("dizziness"))
        displaySymp.add(symptoms_nameList("drying and tingling lips"))
        displaySymp.add(symptoms_nameList("enlarged thyroid"))
        displaySymp.add(symptoms_nameList("excessive hunger"))
        displaySymp.add(symptoms_nameList("extra marital contacts"))
        displaySymp.add(symptoms_nameList("family history"))
        displaySymp.add(symptoms_nameList("fast heart rate"))
        displaySymp.add(symptoms_nameList("fatigue"))
        displaySymp.add(symptoms_nameList("fluid overload"))
        displaySymp.add(symptoms_nameList("foul smell of urine"))
        displaySymp.add(symptoms_nameList("headache"))
        displaySymp.add(symptoms_nameList("high fever"))
        displaySymp.add(symptoms_nameList("hip joint pain"))
        displaySymp.add(symptoms_nameList("history of alcohol consumption"))
        displaySymp.add(symptoms_nameList("increased appetite"))
        displaySymp.add(symptoms_nameList("indigestion"))
        displaySymp.add(symptoms_nameList("inflammatory nails"))
        displaySymp.add(symptoms_nameList("internal itching"))
        displaySymp.add(symptoms_nameList("irregular sugar level"))
        displaySymp.add(symptoms_nameList("irritability"))
        displaySymp.add(symptoms_nameList("irritation in anus"))
        displaySymp.add(symptoms_nameList("joint pain"))
        displaySymp.add(symptoms_nameList("knee pain"))
        displaySymp.add(symptoms_nameList("lack of concentration"))
        displaySymp.add(symptoms_nameList("lethargy"))
        displaySymp.add(symptoms_nameList("loss of appetite"))
        displaySymp.add(symptoms_nameList("loss of balance"))
        displaySymp.add(symptoms_nameList("loss of smell"))
        displaySymp.add(symptoms_nameList("malaise"))
        displaySymp.add(symptoms_nameList("mild fever"))
        displaySymp.add(symptoms_nameList("mood swings"))
        displaySymp.add(symptoms_nameList("movement stiffness"))
        displaySymp.add(symptoms_nameList("mucoid sputum"))
        displaySymp.add(symptoms_nameList("muscle pain"))
        displaySymp.add(symptoms_nameList("muscle wasting"))
        displaySymp.add(symptoms_nameList("muscle weakness"))
        displaySymp.add(symptoms_nameList("nausea"))
        displaySymp.add(symptoms_nameList("neck pain"))
        displaySymp.add(symptoms_nameList("nodal skin eruptions"))
        displaySymp.add(symptoms_nameList("obesity"))
        displaySymp.add(symptoms_nameList("pain behind the eyes"))
        displaySymp.add(symptoms_nameList("pain during bowel movements"))
        displaySymp.add(symptoms_nameList("pain in anal region"))
        displaySymp.add(symptoms_nameList("painful walking"))
        displaySymp.add(symptoms_nameList("palpitations"))
        displaySymp.add(symptoms_nameList("passage of gases"))
        displaySymp.add(symptoms_nameList("patches in throat"))
        displaySymp.add(symptoms_nameList("phlegm"))
        displaySymp.add(symptoms_nameList("polyuria"))
        displaySymp.add(symptoms_nameList("prominent veins on calf"))
        displaySymp.add(symptoms_nameList("puffy face and eyes"))
        displaySymp.add(symptoms_nameList("pus filled pimples"))
        displaySymp.add(symptoms_nameList("receiving blood transfusion"))
        displaySymp.add(symptoms_nameList("receiving unsterile injections"))
        displaySymp.add(symptoms_nameList("red sore around nose"))
        displaySymp.add(symptoms_nameList("red spots over body"))
        displaySymp.add(symptoms_nameList("redness of eyes"))
        displaySymp.add(symptoms_nameList("restlessness"))
        displaySymp.add(symptoms_nameList("runny nose"))
        displaySymp.add(symptoms_nameList("rusty sputum"))
        displaySymp.add(symptoms_nameList("scurring"))
        displaySymp.add(symptoms_nameList("shivering"))
        displaySymp.add(symptoms_nameList("silver like dusting"))
        displaySymp.add(symptoms_nameList("sinus pressure"))
        displaySymp.add(symptoms_nameList("skin peeling"))
        displaySymp.add(symptoms_nameList("skin rash"))
        displaySymp.add(symptoms_nameList("slurred speech"))
        displaySymp.add(symptoms_nameList("small dents in nails"))
        displaySymp.add(symptoms_nameList("spinning movements"))
        displaySymp.add(symptoms_nameList("spotting  urination"))
        displaySymp.add(symptoms_nameList("stiff neck"))
        displaySymp.add(symptoms_nameList("stomach bleeding"))
        displaySymp.add(symptoms_nameList("stomach pain"))
        displaySymp.add(symptoms_nameList("sunken eyes"))
        displaySymp.add(symptoms_nameList("sweating"))
        displaySymp.add(symptoms_nameList("swelled lymph nodes"))
        displaySymp.add(symptoms_nameList("swelling joints"))
        displaySymp.add(symptoms_nameList("swelling of stomach"))
        displaySymp.add(symptoms_nameList("swollen blood vessels"))
        displaySymp.add(symptoms_nameList("swollen extremeties"))
        displaySymp.add(symptoms_nameList("swollen legs"))
        displaySymp.add(symptoms_nameList("throat irritation"))
        displaySymp.add(symptoms_nameList("toxic look (typhos"))
        displaySymp.add(symptoms_nameList("ulcers on tongue"))
        displaySymp.add(symptoms_nameList("unsteadiness"))
        displaySymp.add(symptoms_nameList("visual disturbances"))
        displaySymp.add(symptoms_nameList("vomiting"))
        displaySymp.add(symptoms_nameList("watering from eyes"))
        displaySymp.add(symptoms_nameList("weakness in limbs"))
        displaySymp.add(symptoms_nameList("weakness of one body side"))
        displaySymp.add(symptoms_nameList("weight gain"))
        displaySymp.add(symptoms_nameList("weight loss"))
        displaySymp.add(symptoms_nameList("yellow crust ooze"))
        displaySymp.add(symptoms_nameList("yellow urine"))
        displaySymp.add(symptoms_nameList("yellowing of eyes"))
        displaySymp.add(symptoms_nameList("yellowish skin"))
        displaySymp.add(symptoms_nameList("itching"))


        //showing symptoms in the recycler view
        val myadapter = search_disease_adapterclass(displaySymp, requireContext())
        myRecycler.adapter = myadapter





        return v
    }




    private fun ML_model(Inputarray: ArrayList<String>) {



        val symptomsMap = mapOf(
            "abdominal pain" to 0,
            "abnormal menstruation" to 1,
            "acidity" to 2,
            "acute liver failure" to 3,
            "altered sensorium" to 4,
            "anxiety" to 5,
            "back pain" to 6,
            "belly pain" to 7,
            "blackheads" to 8,
            "bladder discomfort" to 9,
            "blister" to 10,
            "blood in sputum" to 11,
            "bloody stool" to 12,
            "blurred and distorted vision" to 13,
            "breathlessness" to 14,
            "brittle nails" to 15,
            "bruising" to 16,
            "burning micturition" to 17,
            "chest pain" to 18,
            "chills" to 19,
            "cold hands and feets" to 20,
            "coma" to 21,
            "congestion" to 22,
            "constipation" to 23,
            "continuous feel of urine" to 24,
            "continuous sneezing" to 25,
            "cough" to 26,
            "cramps" to 27,
            "dark urine" to 28,
            "dehydration" to 29,
            "depression" to 30,
            "diarrhoea" to 31,
            "dischromic patches" to 32,
            "distention of abdomen" to 33,
            "dizziness" to 34,
            "drying and tingling lips" to 35,
            "enlarged thyroid" to 36,
            "excessive hunger" to 37,
            "extra marital contacts" to 38,
            "family history" to 39,
            "fast heart rate" to 40,
            "fatigue" to 41,
            "fluid overload" to 42,
            "foul smell of urine" to 43,
            "headache" to 44,
            "high fever" to 45,
            "hip joint pain" to 46,
            "history of alcohol consumption" to 47,
            "increased appetite" to 48,
            "indigestion" to 49,
            "inflammatory nails" to 50,
            "internal itching" to 51,
            "irregular sugar level" to 52,
            "irritability" to 53,
            "irritation in anus" to 54,
            "joint pain" to 55,
            "knee pain" to 56,
            "lack of concentration" to 57,
            "lethargy" to 58,
            "loss of appetite" to 59,
            "loss of balance" to 60,
            "loss of smell" to 61,
            "malaise" to 62,
            "mild fever" to 63,
            "mood swings" to 64,
            "movement stiffness" to 65,
            "mucoid sputum" to 66,
            "muscle pain" to 67,
            "muscle wasting" to 68,
            "muscle weakness" to 69,
            "nausea" to 70,
            "neck pain" to 71,
            "nodal skin eruptions" to 72,
            "obesity" to 73,
            "pain behind the eyes" to 74,
            "pain during bowel movements" to 75,
            "pain in anal region" to 76,
            "painful walking" to 77,
            "palpitations" to 78,
            "passage of gases" to 79,
            "patches in throat" to 80,
            "phlegm" to 81,
            "polyuria" to 82,
            "prominent veins on calf" to 83,
            "puffy face and eyes" to 84,
            "pus filled pimples" to 85,
            "receiving blood transfusion" to 86,
            "receiving unsterile injections" to 87,
            "red sore around nose" to 88,
            "red spots over body" to 89,
            "redness of eyes" to 90,
            "restlessness" to 91,
            "runny nose" to 92,
            "rusty sputum" to 93,
            "scurring" to 94,
            "shivering" to 95,
            "silver like dusting" to 96,
            "sinus pressure" to 97,
            "skin peeling" to 98,
            "skin rash" to 99,
            "slurred speech" to 100,
            "small dents in nails" to 101,
            "spinning movements" to 102,
            "spotting urination" to 103,
            "stiff neck" to 104,
            "stomach bleeding" to 105,
            "stomach pain" to 106,
            "sunken eyes" to 107,
            "sweating" to 108,
            "swelled lymph nodes" to 109,
            "swelling joints" to 110,
            "swelling of stomach" to 111,
            "swollen blood vessels" to 112,
            "swollen extremeties" to 113,
            "swollen legs" to 114,
            "throat irritation" to 115,
            "toxic look (typhos)" to 116,
            "ulcers on tongue" to 117,
            "unsteadiness" to 118,
            "visual disturbances" to 119,
            "vomiting" to 120,
            "watering from eyes" to 121,
            "weakness in limbs" to 122,
            "weakness of one body side" to 123,
            "weight gain" to 124,
            "weight loss" to 125,
            "yellow crust ooze" to 126,
            "yellow urine" to 127,
            "yellowing of eyes" to 128,
            "yellowish skin" to 129,
            "itching" to 130
        )


        val DiseaselIST = mapOf(
            0 to "Urinary tract infection",
            1 to "Gastroenteritis",
            2 to "Hypothyroidism",
            3 to "Chicken pox",
            4 to "Cervical spondylosis",
            5 to "Migraine",
            6 to "Hepatitis B",
            7 to "Diabetes ",
            8 to "Hepatitis E",
            9 to "Bronchial Asthma",
            10 to "Hyperthyroidism",
            11 to "(vertigo) Paroymsal  Positional Vertigo",
            12 to "Varicose veins",
            13 to "Dengue",
            14 to "Chronic cholestasis",
            15 to "Paralysis (brain hemorrhage)",
            16 to "Tuberculosis",
            17 to "Hepatitis C",
            18 to "Allergy",
            19 to "Alcoholic hepatitis",
            20 to "Typhoid",
            21 to "Hypertension ",
            22 to "Malaria",
            23 to "Jaundice",
            24 to "Dimorphic hemmorhoids(piles)",
            25 to "Hepatitis D",
            26 to "Heart attack",
            27 to "Osteoarthristis",
            28 to "Drug Reaction",
            29 to "AIDS",
            30 to "Hypoglycemia",
            31 to "Impetigo",
            32 to "GERD",
            33 to "Psoriasis",
            34 to "Pneumonia",
            35 to "hepatitis A",
            36 to "Acne",
            37 to "Peptic ulcer diseae",
            38 to "Arthritis",
            39 to "Common Cold",
            40 to "Fungal infection"
        )


        val DoctorsList = mapOf(
            0 to "Dr.Julie Adkins", 1 to "Dr.Alexander Serrano",
            2 to "Dr. Angela Duncan", 3 to "Dr.Tammy Kennedy", 4 to "Dr.Jane Archer",
            5 to "Dr.Jessica Rosario", 6 to "Dr.Melissa Reyes", 7 to "Dr.Susan Barnes",
            8 to "Dr.Lindsey Jones", 9 to "Dr.Shannon Torres", 10 to "Dr.Nicholas Bell",
            11 to "Dr.Joshua Bruce", 12 to "Dr.Donna Fields", 13 to "Dr.Lisa Gregory",
            14 to "Dr.Randall Mcdonald", 15 to "Dr.Matthew Ford", 16 to "Dr.Cynthia Martin",
            17 to "Dr.Donna Lowery", 18 to "Dr.Cory Douglas", 19 to "Dr.Albert Walker",
            20 to "Dr.Laura Price", 21 to "Dr.Mario Hawkins", 22 to "Dr.Jason Mitchell",
            23 to "Dr.Zachary Obrien", 24 to "Dr.Joy Palmer", 25 to "Dr.Kayla Morrison",
            26 to "Dr.Carolyn Vega", 27 to "Dr.Nicholas Lowe", 28 to "Dr.Ashley Taylor",
            29 to "Dr.Jill Foster", 30 to "Dr.Joseph Medina", 31 to "Dr.Larry Collins",
            32 to "Dr.Stephanie Murphy", 33 to "Dr.Tommy Foster", 34 to "Dr.Lisa Villarreal",
            35 to "Dr.Anthony Chapman", 36 to "Dr.James Rodriguez", 37 to "Dr.Sean Jennings",
            38 to "Dr.Faith Irwin", 39 to "Dr.Linda Anderson", 40 to "Dr.Peggy Kennedy"
        )


        val precaution_list = mapOf<Int, String>(
            0 to "drink plenty of water\n increase vitamin c intake\n drink cranberry juice\n take probiotics",
            1 to "stop eating solid food for while\n try taking small sips of water\n rest\n ease back into eating",
            2 to "reduce stress\n exercise\n eat healthy\n get proper sleep",
            3 to "use neem in bathing \n consume neem leaves\n take vaccine\n avoid public places",
            4 to "use heating pad or cold pack\n exercise\n take otc pain reliver\n consult doctor",
            5 to "meditation\n reduce stress\n use poloroid glasses in sun\n consult doctor",
            6 to "consult nearest hospital\n vaccination\n eat healthy\n medication",
            7 to "have balanced diet\n exercise\n consult doctor\n follow up",
            8 to "stop alcohol consumption\n rest\n consult doctor\n medication",
            9 to "switch to loose cloothing\n take deep breaths\n get away from trigger\n seek help",
            10 to "eat healthy\n massage\n use lemon balm\n take radioactive iodine treatment",
            11 to "lie down\n avoid sudden change in body\n avoid abrupt head movment\n relax",
            12 to "lie down flat and raise the leg high\n use oinments\n use vein compression\n dont stand still for long",
            13 to "drink papaya leaf juice\n avoid fatty spicy food\n keep mosquitos away\n keep hydrated",
            14 to "cold baths\n anti itch medicine\n consult doctor\n eat healthy",
            15 to "massage\n eat healthy\n exercise\n consult doctor",
            16 to "cover mouth\n consult doctor\n medication\n rest",
            17 to "Consult nearest hospital\n vaccination\n eat healthy\n medication",
            18 to "apply calamine\n cover area with bandage\n nan\n use ice to compress itching",
            19 to "stop alcohol consumption\n consult doctor\n medication\n follow up",
            20 to "eat high calorie vegitables\n antiboitic therapy\n consult doctor\n medication",
            21 to "meditation\n salt baths\n reduce stress\n get proper sleep",
            22 to "Consult nearest hospital\n avoid oily food\n avoid non veg food\n keep mosquitos out",
            23 to "drink plenty of water\n consume milk thistle\n eat fruits and high fiberous food\n medication",
            24 to "avoid fatty spicy food\n consume witch hazel\n warm bath with epsom salt\n consume alovera juice",
            25 to "consult doctor\n medication\n eat healthy\n follow up",
            26 to "call ambulance\n chew or swallow asprin\n keep calm\n nan",
            27 to "acetaminophen\n consult nearest hospital\n follow up\n salt baths",
            28 to "stop irritation\n consult nearest hospital\n stop taking drug\n follow up",
            29 to "avoid open cuts\n wear ppe if possible\n consult doctor\n follow up",
            30 to "lie down on side\n check in pulse\n drink sugary drinks\n consult doctor",
            31 to "soak affected area in warm water\n use antibiotics\n remove scabs with wet compressed cloth\n consult doctor",
            32 to "avoid fatty spicy food\n avoid lying down after eating\n maintain healthy weight\n exercise",
            33 to "wash hands with warm soapy water\n stop bleeding using pressure\n consult doctor\n salt baths",
            34 to "consult doctor\n medication\n rest\n follow up",
            35 to "Consult nearest hospital\n wash hands through\n avoid fatty spicy food\n medication",
            36 to "bath twice\n avoid fatty spicy food\n drink plenty of water\n avoid too many products",
            37 to "avoid fatty spicy food\n consume probiotic food\n eliminate milk\n limit alcohol",
            38 to "exercise\n use hot and cold therapy\n try acupuncture\n massage",
            39 to "drink vitamin c rich drinks\n take vapour\n avoid cold food\n keep fever in check",
            40 to "bath twice\n use detol or neem in bathing water\n keep infected area dry\n use clean cloths"
        )



        println("Input Array" + Inputarray)


        val Outarray1 = updateInputArray(Inputarray, symptomsMap)


        //ML model
        val model = Disease1.newInstance(requireContext())


        val byteBuffer  = Outarray1      //bytebuffer and Outarray1 is of same type IntArray type


        // Creates inputs for reference.
        val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 131), DataType.FLOAT32)
        inputFeature0.loadArray(byteBuffer)


        // Runs model inference and gets result.
        val outputs = model.process(inputFeature0)
        val outputFeature0 = outputs.outputFeature0AsTensorBuffer


        val max = getMaxOutput(outputFeature0.floatArray)

        println("Max Value :" + max)

        println("outputs probablity : " + outputFeature0.floatArray[max].toString())

        println("outputs array: " + DiseaselIST[max])

        //showing disease
        results.setText(DiseaselIST[max])
        println("outputs array: " + results)

        //recommending doctors
        Doctors_results.setText(DoctorsList[max])
        println("outputs Doc_array: " + Doctors_results)

        //recommending the preacautions:
        precaution_results.setText(precaution_list[max])
        println("outputs precaution: " + precaution_list[max])



        doctors_recommend_button.setOnClickListener {

            val bundle: Bundle = Bundle()
            bundle.putString(MyKey, DoctorsList[max].toString())

            val fragment: Fragment = Doctors_Fragment()

            fragment.setArguments(bundle)
            val My_activity: AppCompatActivity = it.context as AppCompatActivity

            My_activity.supportFragmentManager.beginTransaction()
                .replace(R.id.wrapper2, fragment).addToBackStack(null)
                .commit()




        }


        // Releases model resources if no longer used.
        model.close()


    }




    //getting max probablity output
    fun getMaxOutput(arr: FloatArray): Int {

        var index = 0
        var min = 0.0f
        for (i in 0..40) {

            if (arr[i] > min) {
                index = i
                min = arr[i]
            }

        }
        return index

    }


    //updating the input symptoms array from 0 to 1
    fun updateInputArray(Inputarray: ArrayList<String>, symptomsMap: Map<String, Int>): IntArray {

        val Outarray = intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)


        val Array = ArrayList<Int>()


        for (i in Inputarray) {

            if (i in symptomsMap.keys) {

                symptomsMap[i]?.let { Array.add(it) }
                Log.d("main", "for loop running")

            }
        }

        for (j in Array) {

            Outarray[j] = 1

        }

        println("emptyArray:" + Array)
        println("Array " + Outarray.toString())


        return Outarray

    }





    companion object {

        val MyKey = "Key"


        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Search_Disease_Fragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }

    }


}










//open class showSymptoms(val sympList: ArrayList<symptoms_nameList>, val mContext:Context): Item<GroupieViewHolder>() {
//
//    var symptomSelectedList = ArrayList<symptoms_nameList>()
//    var selectedItem = false
//
//
//    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
//
//        val symp: symptoms_nameList = sympList[position]
////        val symptoms : symptoms_nameList = sympList[position]
//
//        viewHolder.itemView.item.text = symp.sympName

//showing up the
//        viewHolder.itemView.setOnClickListener(object : View.OnClickListener{
//            override fun onClick(v: View?) {
//
//                selectedItem = true
//                if (symptomSelectedList.contains(sympList.)){
//
//                    viewHolder.itemView.setBackgroundColor(Color.TRANSPARENT)
//                    symptomSelectedList.remove(sympList.get(position))
//
//                }
//                else{
//                    viewHolder.itemView.setBackgroundColor(Color.GRAY)
//                    symptomSelectedList.add(sympList.get(position))
//                }
//                if (symptomSelectedList.size==0){
//
//                    selectedItem = false
//                }
//
//                Toast.makeText(mContext,"Arrays are $symptomSelectedList",Toast.LENGTH_LONG).show()
//
//
//            }
//
//        })
//
//
//    }
//
//    override fun getLayout(): Int {
//
//        return R.layout.symptom_recyler_item
//
//    }
//
//}









//
//<androidx.cardview.widget.CardView
//android:id="@+id/sy1_card"
//app:cardElevation="@dimen/_30sdp"
//app:cardCornerRadius="5dp"
//app:cardBackgroundColor="#EE1B1B1B"
//android:layout_marginStart="15sp"
//android:layout_marginTop="10sp"
//android:layout_marginEnd="15sp"
//android:layout_marginBottom="15sp"
//android:layout_width="match_parent"
//android:layout_height="wrap_content">
//
//<EditText
//android:id="@+id/sy1"
//android:layout_width="match_parent"
//android:layout_height="wrap_content"
//android:padding="@dimen/_5sdp"
//android:hint="Enter Sympton-1"
//android:textColor="@color/white"
//android:textColorHint="@color/gray" />
//</androidx.cardview.widget.CardView>
//
//<androidx.cardview.widget.CardView
//android:id="@+id/sy2_card"
//android:layout_below="@id/sy1_card"
//app:cardElevation="@dimen/_30sdp"
//app:cardCornerRadius="5dp"
//app:cardBackgroundColor="#EE1B1B1B"
//android:layout_marginStart="15sp"
//android:layout_marginTop="10sp"
//android:layout_marginEnd="15sp"
//android:layout_marginBottom="15sp"
//android:layout_width="match_parent"
//android:layout_height="wrap_content">
//
//<EditText
//android:id="@+id/sy2"
//android:layout_width="match_parent"
//android:layout_height="wrap_content"
//android:padding="@dimen/_5sdp"
//android:layout_below="@+id/sy1_card"
//android:hint="Enter Sympton-2"
//android:textColor="@color/white"
//android:textColorHint="@color/gray"/>
//
//</androidx.cardview.widget.CardView>
//
//<androidx.cardview.widget.CardView
//android:id="@+id/sy3_card"
//android:layout_below="@+id/sy2_card"
//android:layout_marginStart="15sp"
//android:layout_marginTop="10sp"
//android:layout_marginEnd="15sp"
//android:layout_marginBottom="15sp"
//app:cardElevation="@dimen/_30sdp"
//app:cardCornerRadius="5dp"
//app:cardBackgroundColor="#EE1B1B1B"
//android:layout_width="match_parent"
//android:layout_height="wrap_content">
//
//<EditText
//android:id="@+id/sy3"
//android:layout_width="match_parent"
//android:layout_height="wrap_content"
//android:padding="@dimen/_5sdp"
//android:layout_below="@+id/sy2"
//android:hint="Enter Sympton-3"
//android:textColor="@color/white"
//android:textColorHint="@color/gray"/>
//
//</androidx.cardview.widget.CardView>


//android:layout_below="@+id/sy3_card"


//    fun save_symptoms_in_db(){
//
//        val symRef = FirebaseDatabase.getInstance().getReference("Symptoms Name/")
//
//
////        val myArray = arrayListOf<String>("\n, Abdominal Pain \n" , "Abnormal Menstruation \n" , "Acidity \n" ,
////            "Acute Liver Failure \n" , "Altered Sensorium \n" , "Anxiety \n" , "Back Pain \n" ,
////            "Belly pain \n" , "Blackheads \n" , "Bladder Discomfort \n" , "Blister \n" ,
////            "Blood in Sputum \n" , "Bloody Stool \n" , "Blurred and Distorted Vision \n" ,
////            "Breathlessness \n" , "Brittle Nails \n" , "Bruising \n" , "Burning Micturition \n" ,
////            "Chest Pain \n" , "Chills \n" , "Cold Hands and Feets \n" , "Coma \n" ,
////            "Congestion \n" , "Constipation \n", "Continuous Feel of Urine \n" ,
////            "Continuous Sneezing \n" , "cough \n" , "Cramps \n" , "Dark Urine \n" ,
////            "Dehydration \n", "Depression \n" , "Diarrhoea \n" , "Dischromic Patches \n" ,
////            "Distention of Abdomen \n" , "Dizziness \n" , "Drying and Tingling Lips \n" ,
////            "Enlarged Thyroid \n" , "Excessive Hunger \n" , "Extra Marital Contacts \n" ,
////            "Family History \n" , "Fast Heart Rate \n" , "Fatigue" , "Fluid Overload \n" ,
////            "Foul Smell of Urine" , "Headache \n" , "High Fever \n" , "Hip Joint Pain \n" ,
////            "History of Alcohol Consumption \n" , "Increased Appetite \n" , "Indigestion \n" ,
////            "Inflammatory Nails \n" , "Internal Itching \n" , "Irregular Sugar Level \n" ,
////            "Irritability \n" , "Irritation in Anus \n" , "Joint Pain \n" , "Knee Pain \n" ,
////            "Lack of Concentration \n" ,"Lethargy \n" ,"Loss of Appetite \n" ,"Loss of Balance \n",
////            "Loss of Smell \n" , "Malaise \n" , "Mild Fever \n" , "Mood Swings \n" ,
////            "Movement Stiffness \n" , "Mucoid Sputum \n" , "Muscle Pain \n" ,
////            "Muscle Wasting \n" , "Muscle Weakness \n" , "Nausea \n" , "Neck Pain \n" ,
////            "Nodal Skin Eruptions \n" , "Obesity" , "Pain Behind the Eyes \n" ,
////            "Pain During Bowel Movements \n" , "Pain in Anal Region \n" , "Painful Walking \n" ,
////            "Palpitations \n" , "Passage of Gases \n" , "Patches in Throat \n" , "Phlegm \n",
////            "Polyuria \n" , "Prominent Veins on Calf \n" , "Puffy Face and Eyes \n" ,
////            "Pus Filled Pimples \n" , "Receiving Blood Transfusion \n" , "Receiving Unsterile Injections \n" ,
////            "Red Sore Around Nose \n" , "Red Spots Over Body \n" , "Redness of Eyes \n" ,
////            "Restlessness \n" , "Runny Nose \n" , "Rusty Sputum \n" , "Scurring \n" , "Shivering \n" ,
////            "Silver Like Dusting \n" , "Sinus Pressure \n" , "Skin Peeling \n" , "Skin Rash \n" ,
////            "Slurred Speech \n" , "Small Dents in Nails \n" , "Spinning Movements \n" ,
////            "Spotting Urination \n" , "Stiff Neck \n", "Stomach Bleeding \n" ,
////            "Stomach Pain \n" , "Sunken Eyes \n" , "Sweating \n" , "Swelled Lymph Nodes \n" ,
////            "Swelling Joints \n", "Swelling of Stomach \n" , "Swollen Blood Vessels \n" ,
////            "Swollen Extremeties \n" , "Swollen Legs \n" , "throat Irritation \n" ,
////            "Toxic Look (Typhos) \n", "Ulcers on Tongue \n" , "Unsteadiness \n" ,
////            "Visual Disturbances \n" , "Vomiting \n", "Watering From Eyes \n" ,
////            "Weakness in Limbs \n" , "Weakness of One Body Side \n" , "Weight Gain \n",
////            "Weight Loss \n" , "Yellow Crust Ooze \n", "Yellow Urine \n" ,
////            "Yellowing of Eyes \n" , "Yellowish Skin \n" , "Itching \n")
//
//
////        val obj = symptoms_nameList()
//
////        symRef.setValue(obj).addOnCompleteListener {
////
////            Log.d("Search Disease","Data saved")
////        }
//
//    }
