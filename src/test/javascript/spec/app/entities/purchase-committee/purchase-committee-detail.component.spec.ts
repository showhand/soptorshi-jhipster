/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { PurchaseCommitteeDetailComponent } from 'app/entities/purchase-committee/purchase-committee-detail.component';
import { PurchaseCommittee } from 'app/shared/model/purchase-committee.model';

describe('Component Tests', () => {
    describe('PurchaseCommittee Management Detail Component', () => {
        let comp: PurchaseCommitteeDetailComponent;
        let fixture: ComponentFixture<PurchaseCommitteeDetailComponent>;
        const route = ({ data: of({ purchaseCommittee: new PurchaseCommittee(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [PurchaseCommitteeDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(PurchaseCommitteeDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PurchaseCommitteeDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.purchaseCommittee).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
