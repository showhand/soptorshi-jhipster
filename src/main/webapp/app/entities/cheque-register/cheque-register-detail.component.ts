import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IChequeRegister } from 'app/shared/model/cheque-register.model';

@Component({
    selector: 'jhi-cheque-register-detail',
    templateUrl: './cheque-register-detail.component.html'
})
export class ChequeRegisterDetailComponent implements OnInit {
    chequeRegister: IChequeRegister;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ chequeRegister }) => {
            this.chequeRegister = chequeRegister;
        });
    }

    previousState() {
        window.history.back();
    }
}
