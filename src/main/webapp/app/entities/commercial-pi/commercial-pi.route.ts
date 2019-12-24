import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CommercialPi, ICommercialPi } from 'app/shared/model/commercial-pi.model';
import { CommercialPiService } from './commercial-pi.service';
import { CommercialPiComponent } from './commercial-pi.component';
import { CommercialPiDetailComponent } from './commercial-pi-detail.component';
import { CommercialPiUpdateComponent } from './commercial-pi-update.component';
import { CommercialPiDeletePopupComponent } from './commercial-pi-delete-dialog.component';

@Injectable({ providedIn: 'root' })
export class CommercialPiResolve implements Resolve<ICommercialPi> {
    constructor(private service: CommercialPiService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICommercialPi> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<CommercialPi>) => response.ok),
                map((commercialPi: HttpResponse<CommercialPi>) => commercialPi.body)
            );
        }
        return of(new CommercialPi());
    }
}

export const commercialPiRoute: Routes = [
    {
        path: '',
        component: CommercialPiComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialPis'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: CommercialPiDetailComponent,
        resolve: {
            commercialPi: CommercialPiResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialPis'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: CommercialPiUpdateComponent,
        resolve: {
            commercialPi: CommercialPiResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialPis'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: CommercialPiUpdateComponent,
        resolve: {
            commercialPi: CommercialPiResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialPis'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const commercialPiPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: CommercialPiDeletePopupComponent,
        resolve: {
            commercialPi: CommercialPiResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialPis'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
