import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDepartmentHead } from 'app/shared/model/department-head.model';

@Component({
    selector: 'jhi-department-head-detail',
    templateUrl: './department-head-detail.component.html'
})
export class DepartmentHeadDetailComponent implements OnInit {
    departmentHead: IDepartmentHead;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ departmentHead }) => {
            this.departmentHead = departmentHead;
        });
    }

    previousState() {
        window.history.back();
    }
}
