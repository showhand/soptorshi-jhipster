/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { DesignationDetailComponent } from 'app/entities/designation/designation-detail.component';
import { Designation } from 'app/shared/model/designation.model';

describe('Component Tests', () => {
    describe('Designation Management Detail Component', () => {
        let comp: DesignationDetailComponent;
        let fixture: ComponentFixture<DesignationDetailComponent>;
        const route = ({ data: of({ designation: new Designation(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [DesignationDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(DesignationDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DesignationDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.designation).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
