import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProvidentFund } from 'app/shared/model/provident-fund.model';

@Component({
    selector: 'jhi-provident-fund-detail',
    templateUrl: './provident-fund-detail.component.html'
})
export class ProvidentFundDetailComponent implements OnInit {
    providentFund: IProvidentFund;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ providentFund }) => {
            this.providentFund = providentFund;
        });
    }

    previousState() {
        window.history.back();
    }
}
