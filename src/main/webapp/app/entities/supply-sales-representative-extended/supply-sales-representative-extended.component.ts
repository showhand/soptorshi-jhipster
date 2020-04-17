import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiAlertService, JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { AccountService } from 'app/core';
import { SupplySalesRepresentativeExtendedService } from './supply-sales-representative-extended.service';
import { SupplySalesRepresentativeComponent } from 'app/entities/supply-sales-representative';

@Component({
    selector: 'jhi-supply-sales-representative-extended',
    templateUrl: './supply-sales-representative-extended.component.html'
})
export class SupplySalesRepresentativeExtendedComponent extends SupplySalesRepresentativeComponent {
    constructor(
        protected supplySalesRepresentativeService: SupplySalesRepresentativeExtendedService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected parseLinks: JhiParseLinks,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService
    ) {
        super(supplySalesRepresentativeService, jhiAlertService, eventManager, parseLinks, activatedRoute, accountService);
    }
}
