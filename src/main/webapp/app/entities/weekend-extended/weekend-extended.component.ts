import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiAlertService, JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { AccountService } from 'app/core';
import { WeekendComponent } from 'app/entities/weekend';
import { WeekendExtendedService } from 'app/entities/weekend-extended/weekend-extended.service';

@Component({
    selector: 'jhi-weekend-extended',
    templateUrl: './weekend-extended.component.html'
})
export class WeekendExtendedComponent extends WeekendComponent {
    constructor(
        protected weekendExtendedService: WeekendExtendedService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected parseLinks: JhiParseLinks,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService
    ) {
        super(weekendExtendedService, jhiAlertService, eventManager, parseLinks, activatedRoute, accountService);
    }

    generateReport() {
        this.weekendExtendedService.generateReport();
    }
}
