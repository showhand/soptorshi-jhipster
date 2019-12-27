import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiAlertService } from 'ng-jhipster';
import { CommercialPiExtendedService } from './commercial-pi-extended.service';
import { CommercialBudgetService } from 'app/entities/commercial-budget';
import { CommercialPiUpdateComponent } from 'app/entities/commercial-pi';

@Component({
    selector: 'jhi-commercial-pi-update-extended',
    templateUrl: './commercial-pi-update-extended.component.html'
})
export class CommercialPiUpdateExtendedComponent extends CommercialPiUpdateComponent {
    constructor(
        protected jhiAlertService: JhiAlertService,
        protected commercialPiService: CommercialPiExtendedService,
        protected commercialBudgetService: CommercialBudgetService,
        protected activatedRoute: ActivatedRoute
    ) {
        super(jhiAlertService, commercialPiService, commercialBudgetService, activatedRoute);
    }
}
