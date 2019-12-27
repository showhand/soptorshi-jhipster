import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CommercialPo, ICommercialPo } from 'app/shared/model/commercial-po.model';
import { CommercialPoService } from './commercial-po.service';
import { CommercialPoComponent } from './commercial-po.component';
import { CommercialPoDetailComponent } from './commercial-po-detail.component';
import { CommercialPoUpdateComponent } from './commercial-po-update.component';
import { CommercialPoDeletePopupComponent } from './commercial-po-delete-dialog.component';

@Injectable({ providedIn: 'root' })
export class CommercialPoResolve implements Resolve<ICommercialPo> {
    constructor(private service: CommercialPoService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICommercialPo> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<CommercialPo>) => response.ok),
                map((commercialPo: HttpResponse<CommercialPo>) => commercialPo.body)
            );
        }
        return of(new CommercialPo());
    }
}

export const commercialPoRoute: Routes = [
    {
        path: '',
        component: CommercialPoComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialPos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: CommercialPoDetailComponent,
        resolve: {
            commercialPo: CommercialPoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialPos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: CommercialPoUpdateComponent,
        resolve: {
            commercialPo: CommercialPoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialPos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: CommercialPoUpdateComponent,
        resolve: {
            commercialPo: CommercialPoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialPos'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const commercialPoPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: CommercialPoDeletePopupComponent,
        resolve: {
            commercialPo: CommercialPoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialPos'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
