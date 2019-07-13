import { DepartmentHeadDetailComponent } from 'app/entities/department-head';
import { ActivatedRoute } from '@angular/router';
import { Component } from '@angular/core';

@Component({
    selector: 'jhi-department-head-extended-detail',
    templateUrl: './department-head-extended-details.component.html'
})
export class DepartmentHeadExtendedDetailsComponent extends DepartmentHeadDetailComponent {
    constructor(protected activatedRoute: ActivatedRoute) {
        super(activatedRoute);
    }
}
