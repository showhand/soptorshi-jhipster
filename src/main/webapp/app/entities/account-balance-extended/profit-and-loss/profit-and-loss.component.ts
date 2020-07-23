import { Component, OnInit } from '@angular/core';
import { Moment } from 'moment';

import { MstAccountExtendedService } from 'app/entities/mst-account-extended';
import { ToastrService } from 'ngx-toastr';

@Component({
    selector: 'jhi-profit-and-loss',
    templateUrl: './profit-and-loss.component.html',
    styles: []
})
export class ProfitAndLossComponent implements OnInit {
    fromDate: Moment;
    toDate: Moment;
    constructor(private mstAccountExtendedService: MstAccountExtendedService, private toastrService: ToastrService) {}

    ngOnInit() {}

    generateReport() {
        if (this.fromDate && this.toDate) {
            this.mstAccountExtendedService.profitAndLoss(this.fromDate.format('DD-MM-YYYY'), this.toDate.format('DD-MM-YYYY'));
        } else {
            this.toastrService.error('From date and to date must be selected');
        }
    }
}
