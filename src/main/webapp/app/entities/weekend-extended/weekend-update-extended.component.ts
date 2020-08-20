import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { WeekendExtendedService } from './weekend-extended.service';
import { WeekendUpdateComponent } from 'app/entities/weekend';

@Component({
    selector: 'jhi-weekend-update-extended',
    templateUrl: './weekend-update-extended.component.html'
})
export class WeekendUpdateExtendedComponent extends WeekendUpdateComponent {
    constructor(protected weekendExtendedService: WeekendExtendedService, protected activatedRoute: ActivatedRoute) {
        super(weekendExtendedService, activatedRoute);
    }
}
