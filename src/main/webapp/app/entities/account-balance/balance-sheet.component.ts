import { Component, OnDestroy, OnInit } from '@angular/core';
import { AccountBalanceExtendedService } from 'app/entities/account-balance-extended';
import { Moment } from 'moment';
import moment = require('moment');

export const enum BalanceSheetFetchType {
    SUMMARIZED,
    DETAILED
}

@Component({
    selector: 'jhi-balance-sheet',
    templateUrl: './balance-sheet.component.html'
})
export class BalanceSheetComponent implements OnInit, OnDestroy {
    asOnDate: any;
    fetchType: BalanceSheetFetchType;

    constructor(private accountBalanceExtendedService: AccountBalanceExtendedService) {}

    ngOnDestroy(): void {}

    ngOnInit(): void {
        this.asOnDate = moment();
        this.fetchType = BalanceSheetFetchType.SUMMARIZED;
    }
}
