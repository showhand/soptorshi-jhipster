import { Component, OnInit } from '@angular/core';
import * as moment from 'moment';

@Component({
    selector: 'jhi-profit-and-loss',
    templateUrl: './profit-and-loss.component.html',
    styles: []
})
export class ProfitAndLossComponent implements OnInit {
    fromDate = moment;
    toDate = moment;
    constructor() {}

    ngOnInit() {}

    generateReport() {}
}
