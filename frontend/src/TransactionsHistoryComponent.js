import {useEffect, useState} from "react";
import axios from "axios";
import '@fortawesome/fontawesome-free/css/all.min.css';
import "./App.css";
import Transaction from "./Transaction";
import Undo from "./Undo";

export default function TransactionsHistoryComponent({transactions, setTransactions}) {
    const [rotateAnimation, setRotateAnimation] = useState(false);
    const width = '700'
    const height = '300'

    const [searchOption, setSearchOption] = useState(false);
    const [deleteOption, setDeleteOption] = useState(false);
    const [filterOption, setFilterOption] = useState(false);
    const [reportsOption, setReportsOption] = useState(false);

    const [minAmount, setMinAmount] = useState("");
    const [endDate, setEndDate] = useState("");
    const [type, setType] = useState(null);

    useEffect(() => {
        reloadTransactions({
            transactions: transactions,
            setTransactions: setTransactions});
    }, [])

    return (
        <div style={{display: 'flex', flexDirection: 'column', alignItems: 'center'}}>
            <div style={{border: '1px solid black', marginTop: 30, width: parseInt(width), borderRadius: 10}}>
                <div style={{display: 'flex', flexDirection: 'row'}}>
                    <h3 style={{marginTop: 5, marginBottom: 5, textAlign: "left", marginLeft: 10}}>Transactions
                        history</h3>
                    <button style={{marginLeft: "auto", background: 0, border: 0}}
                            onClick={() => {
                                reloadTransactions({
                                    minAmount : minAmount === "" ? null : minAmount,
                                    endDate: endDate === "" ? null : endDate,
                                    type: type,
                                    transactions: transactions,
                                    setTransactions: setTransactions
                                })
                                setRotateAnimation(true);
                                setTimeout(() => {
                                    setRotateAnimation(false)
                                }, 500)
                            }}>
                        <i style={{fontSize: 18}}
                           className={"fas fa-rotate " + (rotateAnimation ? "rotate-anim" : "")}></i>
                    </button>
                </div>
                {searchOption ?
                    <div style={{display: 'flex', flexDirection: 'row', borderTop: '1px solid black'}}>
                        <text style={{marginLeft: 5, marginRight: 5}}>Minimum amount:</text>
                        <input type={"number"} value={minAmount} onChange={(e) => {
                            setMinAmount(e.target.value);
                            setType(null)
                        }} placeholder="Enter amount:"/>
                        <text style={{marginLeft: 5, marginRight: 5}}>End date:</text>
                        <input type={"date"} value={endDate} onChange={(e) => {
                            setEndDate(e.target.value)
                            setType(null)
                        }}/>
                        <text style={{marginLeft: 10}}>Type:</text>
                        <label style={{marginRight: 10}}>
                            Enter
                            <input type={"radio"} onChange={() => {
                                setType("enter")
                                setMinAmount("")
                                setEndDate("")
                            }}
                                   checked={type === "enter"}/>
                        </label>
                        <label>
                            Exit
                            <input type={"radio"} onChange={() => {
                                setType("exit")
                                setMinAmount("")
                                setEndDate("")
                            }}
                                   checked={type === "exit"}/>
                        </label>
                        <button style={{marginLeft: 10}} onClick={() => {
                            reloadTransactions({
                                minAmount : minAmount === "" ? null : minAmount,
                                endDate: endDate === "" ? null : endDate,
                                type: type,
                                transactions: transactions,
                                setTransactions: setTransactions
                                })
                        }}>Apply</button>
                    </div>
                    : null}
                {transactions.length === 0 ?
                    <div style={{borderTop: '1px solid black', marginBottom: 0, width: parseInt(width)}}>
                        <p style={{
                            marginTop: 5,
                            marginBottom: 5,
                            textAlign: "left",
                            marginLeft: 10
                        }}>No transactions available</p>
                    </div> :
                    <div style={{
                        display: 'flex',
                        flexDirection: 'column',
                        maxHeight: parseInt(height),
                        overflowY: 'scroll',
                        marginBottom: 5
                    }}>
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
            <div style={{display: 'flex', flexDirection: 'row', alignItems: 'center', marginTop: 20}}>
                <label style={{marginRight: 10}}>
                    <input type={"radio"} checked={searchOption}
                           onChange={() => {
                               setSearchOption(true)
                               setMinAmount("")
                               setEndDate("")
                               setType(null)

                               setDeleteOption(false)
                               setFilterOption(false)
                               setReportsOption(false)
                           }}/>
                    Search
                </label>
                <label style={{marginRight: 10}}>
                    <input type={"radio"} checked={deleteOption}
                           onChange={() => {
                               setSearchOption(false)
                               setMinAmount("")
                               setEndDate("")
                               setType(null)

                               setDeleteOption(true)
                               setFilterOption(false)
                               setReportsOption(false)
                           }}/>
                    Advanced delete
                </label>
                <label style={{marginRight: 10}}>
                    <input type={"radio"} checked={filterOption}
                           onChange={() => {
                               setSearchOption(false)
                               setMinAmount("")
                               setEndDate("")
                               setType(null)

                               setDeleteOption(false)
                               setFilterOption(true)
                               setReportsOption(false)
                           }}/>
                    Filter
                </label>
                <label style={{marginRight: 20}}>
                    <input type={"radio"} checked={reportsOption}
                           onChange={() => {
                               setSearchOption(false)
                               setMinAmount("")
                               setEndDate("")
                               setType(null)

                               setDeleteOption(false)
                               setFilterOption(false)
                               setReportsOption(true)
                           }}/>
                    Reports
                </label>
                <Undo transactions={transactions} setTransactions={setTransactions}></Undo>
                <button style={{marginLeft: 20}} onClick={() => {
                    setSearchOption(false)
                    setMinAmount("")
                    setEndDate("")
                    setType(null)

                    setDeleteOption(false)
                    setFilterOption(false)
                    setReportsOption(false)
                    reloadTransactions({
                        transactions: transactions,
                        setTransactions: setTransactions
                    });
                }}>Clear</button>
            </div>
        </div>
    );
}

export function reloadTransactions({minAmount = null, endDate = null, type = null, transactions, setTransactions} = {}) {
    if(minAmount != null) {
        if(endDate != null)
            getTransactions({
                minAmount : minAmount,
                endDate : endDate
            }).then(result => {
                setTransactions(result);
            })
        else
            getTransactions({
                minAmount: minAmount
            }).then(result => {
                setTransactions(result);
            })
    }
    else if(type != null)
        getTransactions({
            type: type
        }).then(result => {
            setTransactions(result);
        })
    else
        getTransactions().then(result => {
            setTransactions(result);
        })
}

async function getTransactions({minAmount = null, endDate = null, type = null} = {}) {
    return (await axios.get('http://localhost:8080/get', {
        params: {
            minAmount: minAmount,
            dateEnd: endDate,
            type: type
        }
    })).data;
}