import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { PredefinedNarration } from 'app/shared/model/predefined-narration.model';
import { PredefinedNarrationExtendedService } from './predefined-narration-extended.service';
import { PredefinedNarrationExtendedComponent } from './predefined-narration-extended.component';
import { PredefinedNarrationExtendedDetailComponent } from './predefined-narration-extended-detail.component';
import { PredefinedNarrationExtendedUpdateComponent } from './predefined-narration-extended-update.component';
import { IPredefinedNarration } from 'app/shared/model/predefined-narration.model';
import { PredefinedNarrationDeletePopupComponent } from 'app/entities/predefined-narration';

@Injectable({ providedIn: 'root' })
export class PredefinedNarrationExtendedResolve implements Resolve<IPredefinedNarration> {
    constructor(private service: PredefinedNarrationExtendedService) {}

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

export const predefinedNarrationExtendedRoute: Routes = [
    {
        path: '',
        component: PredefinedNarrationExtendedComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PredefinedNarrations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: PredefinedNarrationExtendedDetailComponent,
        resolve: {
            predefinedNarration: PredefinedNarrationExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PredefinedNarrations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: PredefinedNarrationExtendedUpdateComponent,
        resolve: {
            predefinedNarration: PredefinedNarrationExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PredefinedNarrations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: PredefinedNarrationExtendedUpdateComponent,
        resolve: {
            predefinedNarration: PredefinedNarrationExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PredefinedNarrations'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const predefinedNarrationExtendedPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: PredefinedNarrationDeletePopupComponent,
        resolve: {
            predefinedNarration: PredefinedNarrationExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PredefinedNarrations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
