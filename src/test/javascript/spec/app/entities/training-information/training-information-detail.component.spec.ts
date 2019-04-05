/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { TrainingInformationDetailComponent } from 'app/entities/training-information/training-information-detail.component';
import { TrainingInformation } from 'app/shared/model/training-information.model';

describe('Component Tests', () => {
    describe('TrainingInformation Management Detail Component', () => {
        let comp: TrainingInformationDetailComponent;
        let fixture: ComponentFixture<TrainingInformationDetailComponent>;
        const route = ({ data: of({ trainingInformation: new TrainingInformation(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [TrainingInformationDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(TrainingInformationDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TrainingInformationDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.trainingInformation).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
