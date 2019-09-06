import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { PredefinedNarration } from 'app/shared/model/predefined-narration.model';
import { PredefinedNarrationService } from './predefined-narration.service';
import { PredefinedNarrationComponent } from './predefined-narration.component';
import { PredefinedNarrationDetailComponent } from './predefined-narration-detail.component';
import { PredefinedNarrationUpdateComponent } from './predefined-narration-update.component';
import { PredefinedNarrationDeletePopupComponent } from './predefined-narration-delete-dialog.component';
import { IPredefinedNarration } from 'app/shared/model/predefined-narration.model';

@Injectable({ providedIn: 'root' })
export class PredefinedNarrationResolve implements Resolve<IPredefinedNarration> {
    constructor(private service: PredefinedNarrationService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPredefinedNarration> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<PredefinedNarration>) => response.ok),
                map((predefinedNarration: HttpResponse<PredefinedNarration>) => predefinedNarration.body)
            );
        }
        return of(new PredefinedNarration());
    }
}

export const predefinedNarrationRoute: Routes = [
    {
        path: '',
        component: PredefinedNarrationComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PredefinedNarrations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: PredefinedNarrationDetailComponent,
        resolve: {
            predefinedNarration: PredefinedNarrationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PredefinedNarrations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: PredefinedNarrationUpdateComponent,
        resolve: {
            predefinedNarration: PredefinedNarrationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PredefinedNarrations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: PredefinedNarrationUpdateComponent,
        resolve: {
            predefinedNarration: PredefinedNarrationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PredefinedNarrations'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const predefinedNarrationPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: PredefinedNarrationDeletePopupComponent,
        resolve: {
            predefinedNarration: PredefinedNarrationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PredefinedNarrations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
