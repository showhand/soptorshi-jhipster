/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { AdvanceDetailComponent } from 'app/entities/advance/advance-detail.component';
import { Advance } from 'app/shared/model/advance.model';

describe('Component Tests', () => {
    describe('Advance Management Detail Component', () => {
        let comp: AdvanceDetailComponent;
        let fixture: ComponentFixture<AdvanceDetailComponent>;
        const route = ({ data: of({ advance: new Advance(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [AdvanceDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(AdvanceDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AdvanceDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.advance).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
