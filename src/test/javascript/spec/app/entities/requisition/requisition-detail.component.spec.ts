/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { RequisitionDetailComponent } from 'app/entities/requisition/requisition-detail.component';
import { Requisition } from 'app/shared/model/requisition.model';

describe('Component Tests', () => {
    describe('Requisition Management Detail Component', () => {
        let comp: RequisitionDetailComponent;
        let fixture: ComponentFixture<RequisitionDetailComponent>;
        const route = ({ data: of({ requisition: new Requisition(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [RequisitionDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(RequisitionDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RequisitionDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.requisition).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
