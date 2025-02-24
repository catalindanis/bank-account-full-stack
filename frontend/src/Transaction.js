import '@fortawesome/fontawesome-free/css/all.min.css';
import {useState} from "react";
import axios from "axios";
import {reloadTransactions} from "./TransactionsHistoryComponent";
import {validateTransaction} from "./AddTransactionComponent";

export default function Transaction({id, amount, date, type, transactions, setTransactions}){
    const [amountState, setAmountState] = useState(amount);
    const [dateState, setDateState] = useState(date);
    const [typeState, setTypeState] = useState(type);
    const [operationMessage, setOperationMessage] = useState("");

    return (
        <div>
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
                <input type={'number'} value={amountState} onChange={(e) => {
                    setAmountState(e.target.value)
                }} style={{width: 50}}></input>
                <p style={{marginLeft: 15, marginRight: 5}}>Date:</p>
                <input type={'date'} value={dateState} onChange={(e) => {
                    setDateState(e.target.value)
                }}></input>
                <p style={{marginLeft: 15, marginRight: 5}}>Type:</p>
                <div style={{marginRight: 20}}>
                    <label style={{marginRight: 10}}>
                        <input type={"radio"} checked={typeState === "enter"} onChange={() => setTypeState("enter")}/>
                        Enter
                    </label>
                    <label>
                        <input type={"radio"} checked={typeState === "exit"} onChange={() => setTypeState("exit")}/>
                        Exit
                    </label>
                </div>
                <button style={{marginLeft: "auto", marginRight: 10}}
                onClick={() => {
                    const validateResult = validateTransaction(amountState, dateState, typeState);
                    if(validateResult !== true) {
                        setOperationMessage(validateResult);
                        return;
                    }

                    const result = updateTransaction(id, amountState, dateState, typeState, transactions, setTransactions);
                    setOperationMessage(result);
                }}>
                    Save
                </button>
                <button style={{marginRight: 10}}>
                    Delete
                </button>
            </div>
            <p style={{marginTop: 5, marginBottom: 5}}>{operationMessage}</p>
        </div>
    );
}

async function updateTransaction(id, amount, date, type, transactions, setTransactions){
    try{
        const response = await axios.put('http://localhost:8080/update', null,
            {
                params: {
                    id: id,
                    date: date.toString(),
                    amount: amount,
                    type: type.toString(),
                }
            })
        if(response.status === 200) {
            reloadTransactions(transactions, setTransactions);
            return null;
        }
        return response.data;
    }
    catch (error) {
        return "An unknown error occurred. Check the log to see more details."
    }
}