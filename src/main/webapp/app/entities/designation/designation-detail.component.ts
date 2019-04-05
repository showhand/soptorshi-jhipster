import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDesignation } from 'app/shared/model/designation.model';

@Component({
    selector: 'jhi-designation-detail',
    templateUrl: './designation-detail.component.html'
})
export class DesignationDetailComponent implements OnInit {
    designation: IDesignation;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ designation }) => {
            this.designation = designation;
        });
    }

    previousState() {
        window.history.back();
    }
}
