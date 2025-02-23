import '@fortawesome/fontawesome-free/css/all.min.css';
import {useState} from "react";

export default function Transaction({id, amountVal, dateVal, typeVal}){
    const [amount, setAmount] = useState(amountVal);
    const [date, setDate] = useState(dateVal);
    const [type, setType] = useState(typeVal);

    return (
        <div style={{
            width: '98%',
            margin: 'auto',
            height: 40,
            alignItems: 'center',
            display: 'flex',
            flexDirection: 'row',
            border: '1px solid black',
            borderRadius: 10,
            marginTop: 0,
            marginBottom: 0,
        }}>
            <p style={{marginLeft: 10, marginRight: 5}}>Amount:</p>
            <input type={'number'} value={amount} style={{width: 50}}></input>
            <p style={{marginLeft: 15, marginRight: 5}}>Date:</p>
            <input type={'date'} defaultValue={date}></input>
            <p style={{marginLeft: 15, marginRight: 5}}>Type:</p>
            <div style={{marginRight: 20}}>
                <label style={{marginRight: 10}}>
                    <input type={"radio"}/>
                    Enter
                </label>
                <label>
                    <input type={"radio"}/>
                    Exit
                </label>
            </div>
            <button style={{marginLeft: "auto", marginRight: 10}}>
                Save
            </button>
            <button style={{marginRight: 10}}>
                Delete
            </button>
        </div>
    );
}