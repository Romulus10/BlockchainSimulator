import { Transaction } from './transaction';

export interface Block {
    transactions: Transaction[];
    signature: string;
    previousBlockHash: string;
    hash: string;
}
