import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiAlertService, JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { AccountService } from 'app/core';
import { WeekendComponent, WeekendService } from 'app/entities/weekend';

@Component({
    selector: 'jhi-weekend-extended',
    templateUrl: './weekend-extended.component.html'
})
export class WeekendExtendedComponent extends WeekendComponent {
    constructor(
        protected weekendService: WeekendService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected parseLinks: JhiParseLinks,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService
    ) {
        super(weekendService, jhiAlertService, eventManager, parseLinks, activatedRoute, accountService);
    }
}
