import {useEffect, useState} from "react";
import axios from "axios";
import '@fortawesome/fontawesome-free/css/all.min.css';
import "./App.css";
import Transaction from "./Transaction";

export default function TransactionsHistoryComponent({transactions, setTransactions}) {
    const [rotateAnimation, setRotateAnimation] = useState(false);
    const width = '700'
    const height = '300'

    useEffect(() => {
        reloadTransactions(transactions, setTransactions);
    }, [])

    return (
        <div style={{border: '1px solid black', marginTop: 30, width: parseInt(width), borderRadius: 10}}>
            <div style={{display: 'flex', flexDirection: 'row'}}>
                <h3 style={{marginTop: 5, marginBottom: 5, textAlign: "left", marginLeft: 10}}>Transactions history</h3>
                <button style={{marginLeft: "auto", background: 0, border: 0}}
                onClick={() => {
                    reloadTransactions(transactions, setTransactions);
                    setRotateAnimation(true);
                    setTimeout(() => {setRotateAnimation(false)}, 500)
                }}>
                    <i style={{fontSize: 18}} className={"fas fa-rotate " + (rotateAnimation ? "rotate-anim" : "")}></i>
                </button>
            </div>
            {transactions.length === 0 ?
                <div style={{border: '1px solid black', marginBottom: 0, width: parseInt(width) - 1}}>
                    <p style={{
                    marginTop: 5,
                    marginBottom: 5,
                    textAlign: "left",
                    marginLeft: 10}}>Transactions will appear here</p>
                </div>:
                <div style={{display: 'flex', flexDirection: 'column', maxHeight: parseInt(height), overflowY: 'scroll', marginBottom: 5}}>
                    {transactions.map((transaction) => (
                        <Transaction key={transaction["id"]}
                                     id={transaction["id"]}
                                     type={transaction["type"]}
                                     amount={transaction["amount"]}
                                     date={transaction["date"]}
                                    transactions={transactions}
                                    setTransactions={setTransactions}>
                        </Transaction>
                    ))}
                </div>
            }
        </div>
    );
}

export function reloadTransactions(transactions, setTransactions){
    getTransactions().then(result => {
        setTransactions(result);
        //console.log(transactions);
    })
}

async function getTransactions(){
    return (await axios.get('http://localhost:8080/get')).data;
}