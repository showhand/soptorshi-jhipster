import { DesignationWiseAllowanceDetailComponent } from 'app/entities/designation-wise-allowance';
import { ActivatedRoute } from '@angular/router';
import { Component } from '@angular/core';

@Component({
    selector: 'jhi-designation-wise-allowance-extended-details',
    templateUrl: './designation-wise-allowance-extended-details.component.html'
})
export class DesignationWiseAllowanceExtendedDetailsComponent extends DesignationWiseAllowanceDetailComponent {
    constructor(protected activatedRoute: ActivatedRoute) {
        super(activatedRoute);
    }
}
